package epermit.data.commandhandlers;

import javax.transaction.Transactional;
import epermit.common.CommandResult;
import epermit.data.entities.ReceivedMessage;
import epermit.data.repositories.ReceivedMessageRepository;

public class ReceiveMessageCommandHandler {
    private final ReceivedMessageRepository repository;

    public ReceiveMessageCommandHandler(ReceivedMessageRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public CommandResult execute() {
        ReceivedMessage message = new ReceivedMessage();   
        /*if (handled) {
            m.setHandled(true);
            m.setHandledAt(OffsetDateTime.now());
            repository.save(m);
        }*/
        repository.save(message);
        return CommandResult.success();
    }
}
