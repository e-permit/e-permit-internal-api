package epermit.data.commandhandlers;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import javax.transaction.Transactional;
import com.google.gson.Gson;
import an.awesome.pipelinr.Command;
import epermit.commands.CreatePermitCommand;
import epermit.common.CommandResult;
import epermit.common.MessageType;
import epermit.data.entities.IssuedPermit;
import epermit.data.repositories.IssuedPermitRepository;
import epermit.data.utils.MessageUtil;
import epermit.data.utils.PermitUtil;
import epermit.messages.CreatePermitMessage;
import lombok.SneakyThrows;

public class CreatePermitCommandHandler
                implements Command.Handler<CreatePermitCommand, CommandResult> {
        private final IssuedPermitRepository repository;
        private final PermitUtil permitUtil;
        private final MessageUtil messageUtil;

        public CreatePermitCommandHandler(IssuedPermitRepository repository, PermitUtil permitUtil,
                        MessageUtil messageUtil) {
                this.repository = repository;
                this.permitUtil = permitUtil;
                this.messageUtil = messageUtil;
        }

        @Override
        @Transactional
        @SneakyThrows
        public CommandResult handle(CreatePermitCommand cmd) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                Gson gson = new Gson();
                Integer pid = permitUtil.generatePermitId(cmd.getIssuedFor(), cmd.getPermitYear(),
                                cmd.getPermitType());
                if (pid == null) {
                        
                }
                String serialNumber = permitUtil.getSerialNumber(cmd.getIssuedFor(),
                                cmd.getPermitType(), cmd.getPermitYear(), pid);
                String qrCode = permitUtil.createPermitQrCode(cmd.getIssuedFor(),
                                cmd.getPlateNumber(), cmd.getPermitType(), cmd.getPermitYear(), pid,
                                cmd.getCompanyName());
                IssuedPermit cred = new IssuedPermit();
                cred.setIssuedFor(cmd.getIssuedFor());
                cred.setClaims(gson.toJson(cmd.getClaims()));
                cred.setQrCode(qrCode);
                cred.setCompanyName(cmd.getCompanyName());
                cred.setExpireAt(OffsetDateTime.now().plusYears(1).format(dtf));
                cred.setIssuedAt(OffsetDateTime.now().format(dtf));
                cred.setPermitId(pid);
                cred.setPermitType(cmd.getPermitType());
                cred.setPermitYear(cmd.getPermitYear());
                cred.setPlateNumber(cmd.getPlateNumber());
                cred.setSerialNumber(serialNumber);
                repository.save(cred);
                CreatePermitMessage message = CreatePermitMessage.builder()
                                .companyName(cmd.getCompanyName()).permitId(pid)
                                .permitType(cmd.getPermitType()).permitYear(cmd.getPermitYear())
                                .plateNumber(cmd.getPlateNumber()).claims(cmd.getClaims())
                                .serialNumber(serialNumber).build();
                message.setIssuedFor(cmd.getIssuedFor());
                message.setMessageType(MessageType.CREATE_PERMIT);
                messageUtil.publish(message);
                CommandResult result = CommandResult.success();
                return result;
        }
}
