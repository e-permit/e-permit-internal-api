package epermit.data.commandhandlers;

import javax.transaction.Transactional;
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

    public CreatePermitCommandHandler(IssuedPermitRepository repository,
            PermitUtil permitUtil, MessageUtil messageUtil) {
        this.repository = repository;
        this.permitUtil = permitUtil;
        this.messageUtil = messageUtil;
    }

    @Override
    @Transactional
    @SneakyThrows
    public CommandResult handle(CreatePermitCommand cmd) {
        int pid = permitUtil.generatePermitId(cmd.getIssuedFor(), cmd.getPermitYear(),
                cmd.getPermitType());
        String qrCode = permitUtil.createPermitQrCode(cmd.getIssuedFor(), cmd.getPlateNumber(),
                cmd.getPermitType(), cmd.getPermitYear(), pid, cmd.getCompanyName());
        IssuedPermit cred = new IssuedPermit();
        cred.setAud(cmd.getIssuedFor());
        cred.setAud(cmd.getIssuedFor());
        cred.setQrcode(qrCode);
        cred.setCn(cmd.getCompanyName());
        cred.setCn(cmd.getCompanyName());
        cred.setCn(cmd.getCompanyName());
        cred.setCn(cmd.getCompanyName());
        cred.setCn(cmd.getCompanyName());
        cred.setCn(cmd.getCompanyName());
        cred.setCn(cmd.getCompanyName());
        repository.save(cred);
        CreatePermitMessage message =
                CreatePermitMessage.builder().companyName(cmd.getCompanyName()).permitId(pid)
                        .permitType(cmd.getPermitType().name()).permitYear(cmd.getPermitYear())
                        .plateNumber(cmd.getPlateNumber()).claims(cmd.getClaims()).build();
        message.setAudience(cmd.getIssuedFor());
        message.setMessageType(MessageType.CREATE_PERMIT);
        messageUtil.publish(message);
        CommandResult result = CommandResult.success();
        return result;
    }
}
