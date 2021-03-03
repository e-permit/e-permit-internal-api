package epermit.data.tasks;

import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import epermit.data.entities.ReceivedMessage;
import epermit.data.repositories.ReceivedMessageRepository;
import epermit.messages.CreateKeyMessage;

@Component
public class ReceivedMessageTask {
    private final TransactionTemplate tx;
    private final ReceivedMessageRepository repository;

    public ReceivedMessageTask(TransactionTemplate tx, ReceivedMessageRepository repository) {
        this.tx = tx;
        this.repository = repository;
    }

    @Scheduled(fixedRate = 300 * 1000)
    public void handleMessage() {
        tx.executeWithoutResult(s -> {
            List<ReceivedMessage> messages = repository.findFirst10ByHandledFalse();
            messages.forEach(m -> {
                //CreatePermitMessage.init("issuer", "audience").
                CreateKeyMessage.builder().jwk("jwk").keyId("keyId").build();
                boolean handled = true;// handlerMap.get(m.getMessageType().name()).execute(m.getId());
                if (handled) {
                    // m.setHandled(true);
                    // m.setHandledAt(OffsetDateTime.now());
                    repository.save(m);
                }
            });
        });
    }
}

