package epermit.data.tasks;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import epermit.core.messages.ReceivedMessageHandler;
import epermit.data.entities.ReceivedMessage;
import epermit.data.repositories.ReceivedMessageRepository;

@Component
public class ReceivedMessageTask {
    private final TransactionTemplate tx;
    private final ReceivedMessageRepository repository;
    private Map<String, ReceivedMessageHandler> handlerMap;

    public ReceivedMessageTask(TransactionTemplate tx, ReceivedMessageRepository repository) {
        this.tx = tx;
        this.repository = repository;
    }

    @Scheduled(fixedRate = 300 * 1000)
    public void handleMessage() {
         tx.executeWithoutResult(s -> {
             List<ReceivedMessage> messages = repository.findFirst10ByHandledFalse();
             messages.forEach(m -> {
                  boolean handled = handlerMap.get(m.getMessageType().name()).execute(m.getId());
                  if(handled){
                      m.setHandled(true);
                      m.setHandledAt(OffsetDateTime.now());
                      repository.save(m);
                  }
             });
        });
    }
}

