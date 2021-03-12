package epermit.data.messagehandlers;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.springframework.transaction.annotation.Transactional;
import an.awesome.pipelinr.Command;
import epermit.common.MessageHandleResult;
import epermit.data.entities.Authority;
import epermit.data.entities.VerifierQuota;
import epermit.data.repositories.AuthorityRepository;
import epermit.messages.CreateQuotaMessage;
import lombok.SneakyThrows;

public class CreateQuotaMessageHandler
        implements Command.Handler<CreateQuotaMessage, MessageHandleResult> {

    private final AuthorityRepository repository;

    public CreateQuotaMessageHandler(AuthorityRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    @SneakyThrows
    public MessageHandleResult handle(CreateQuotaMessage m) {
        Authority authority = repository.findByCode(m.getIssuer()).get();
        VerifierQuota quota = new VerifierQuota();
   
        authority.addVerifierQuota(quota);
        repository.save(authority);
        return MessageHandleResult.success();
    }
}


