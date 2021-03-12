package epermit.data.messagehandlers;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.springframework.transaction.annotation.Transactional;
import an.awesome.pipelinr.Command;
import epermit.common.MessageHandleResult;
import epermit.data.entities.Authority;
import epermit.data.entities.AuthorityKey;
import epermit.data.repositories.AuthorityRepository;
import epermit.messages.CreateKeyMessage;
import lombok.SneakyThrows;

public class CreateKeyMessageHandler
        implements Command.Handler<CreateKeyMessage, MessageHandleResult> {

    private final AuthorityRepository repository;

    public CreateKeyMessageHandler(AuthorityRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    @SneakyThrows
    public MessageHandleResult handle(CreateKeyMessage m) {
        Authority authority = repository.findByCode(m.getIssuer()).get();
        AuthorityKey key = new AuthorityKey();
        key.setAuthority(authority);
        key.setContent(m.getJwk());
        key.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        key.setKid(m.getKeyId());
        authority.addKey(key);
        repository.save(authority);
        return MessageHandleResult.success();
    }
}

