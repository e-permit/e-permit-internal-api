package epermit.data.tasks;

import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import epermit.common.CreatedMessageState;
import epermit.data.entities.CreatedMessage;
import epermit.data.repositories.CreatedMessageRepository;

@Component
public class CreatedMessageTask {
    private final TransactionTemplate tx;
    private final CreatedMessageRepository repository;

    public CreatedMessageTask(TransactionTemplate tx, CreatedMessageRepository repository) {
        this.tx = tx;
        this.repository = repository;
    }

    @Scheduled(fixedRate = 300 * 1000)
    public void sendMessage() {
        /*List<CreatedMessage> entities = tx.execute(state -> {
            List<CreatedMessage> messages = repository.findFirst10ByState(CreatedMessageState.NEW);
            messages.forEach(message -> {
                message.setState(CreatedMessageState.LOCKED);
                message.setLockedAt(OffsetDateTime.now());
                repository.save(message);
            });
            return messages;
        });
        // send messages(timeout, fail, success)
        tx.executeWithoutResult(s -> {
            entities.forEach(message -> {
                message.setState(CreatedMessageState.SENDED);
                message.setSendedAt(OffsetDateTime.now());
                repository.save(message);
            });
        });*/
    }
}
