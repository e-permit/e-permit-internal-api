package epermit.data.commandhandlers;

import javax.transaction.Transactional;
import an.awesome.pipelinr.Command;
import epermit.commands.CreatePermitCommand;
import epermit.common.CommandResult;
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
                Integer pid = permitUtil.generatePermitId(cmd.getIssuedFor(), cmd.getPermitYear(),
                                cmd.getPermitType());
                if (pid == null) {

                }
                IssuedPermit permit = permitUtil.getPermitFromCommand(cmd, pid);
                repository.save(permit);
                CreatePermitMessage message = messageUtil.getCreatePermitMessage(permit);
                messageUtil.publish(message);
                CommandResult result = CommandResult.success();
                return result;
        }
}
