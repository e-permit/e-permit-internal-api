package epermit.data.commandhandlers;

import java.time.OffsetDateTime;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;
import an.awesome.pipelinr.Command;
import epermit.commands.CreateAuthorityCommand;
import epermit.common.CommandResult;
import epermit.data.entities.Authority;
import epermit.data.repositories.AuthorityRepository;
import lombok.SneakyThrows;

@Component
public class CreateAuthorityCommandHandler
        implements Command.Handler<CreateAuthorityCommand, CommandResult> {

    private final AuthorityRepository repository;

    public CreateAuthorityCommandHandler(AuthorityRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    @SneakyThrows
    public CommandResult handle(CreateAuthorityCommand command) {
        Authority authority = new Authority();
        authority.setCode(command.getCode());
        authority.setName(command.getName());
        authority.setUri(command.getUri());
        authority.setCreatedAt(OffsetDateTime.now());
        repository.save(authority);
        return CommandResult.success();
    }

}
