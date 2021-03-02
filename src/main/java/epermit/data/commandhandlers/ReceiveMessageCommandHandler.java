package epermit.data.commandhandlers;

import javax.transaction.Transactional;
import an.awesome.pipelinr.Command;
import epermit.commands.ReceiveMessageCommand;
import epermit.common.CommandResult;
import epermit.common.MessageResult;
import epermit.data.entities.ReceivedMessage;
import epermit.data.repositories.ReceivedMessageRepository;

public class ReceiveMessageCommandHandler
        implements Command.Handler<ReceiveMessageCommand, MessageResult> {
    private final ReceivedMessageRepository repository;

    public ReceiveMessageCommandHandler(ReceivedMessageRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public MessageResult handle(ReceiveMessageCommand cmd) {
        MessageResult result = new MessageResult();
        switch (cmd.getMessageType()) {
            case CREATE_KEY:
                break;
            case CREATE_PERMIT:
                break;
            case CREATE_QUOTA:
                break;
            case PERMIT_USED:
                break;
            case QUOTA_CREATED:
                break;
            case REVOKE_KEY:
                break;
            case REVOKE_PERMIT:
                break;
        }
        ReceivedMessage message = new ReceivedMessage();
        /*
         * if (handled) { m.setHandled(true); m.setHandledAt(OffsetDateTime.now());
         * repository.save(m); }
         */
        repository.save(message);
        return MessageResult.success("");
    }
}
