package epermit.data.messagehandlers;

import org.springframework.transaction.annotation.Transactional;
import an.awesome.pipelinr.Command;
import epermit.common.MessageHandleResult;
import epermit.data.entities.Permit;
import epermit.data.repositories.PermitRepository;
import epermit.data.utils.MessageUtil;
import epermit.messages.CreatePermitMessage;
import lombok.SneakyThrows;

public class CreatePermitMessageHandler
        implements Command.Handler<CreatePermitMessage, MessageHandleResult> {

    private final PermitRepository repository;
    private final MessageUtil messageUtil;

    public CreatePermitMessageHandler(PermitRepository repository, MessageUtil messageUtil) {
        this.repository = repository;
        this.messageUtil = messageUtil;
    }

    @Override
    @Transactional
    @SneakyThrows
    public MessageHandleResult handle(CreatePermitMessage m) {
       Permit p =  messageUtil.convertMessageToPermit(m);
       repository.save(p);
       return MessageHandleResult.success();
    }
}

