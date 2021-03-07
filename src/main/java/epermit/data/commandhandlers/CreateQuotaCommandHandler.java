package epermit.data.commandhandlers;

import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import an.awesome.pipelinr.Command;
import epermit.commands.CreateQuotaCommand;
import epermit.common.CommandResult;
import epermit.data.entities.Authority;
import epermit.data.entities.IssuerQuota;
import epermit.data.repositories.AuthorityRepository;
import lombok.SneakyThrows;

@Component
public class CreateQuotaCommandHandler
        implements Command.Handler<CreateQuotaCommand, CommandResult> {

    private final AuthorityRepository repository;

    public CreateQuotaCommandHandler(AuthorityRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    @SneakyThrows
    public CommandResult handle(CreateQuotaCommand command) {
        Authority authority = repository.findByCode(command.getAuthorityCode()).get();
        IssuerQuota quota = new IssuerQuota();
        quota.setAuthority(authority);
        quota.setPermitType(command.getPermitType());
        quota.setEndNumber(command.getEndId());
        quota.setStartNumber(command.getStartId());
        quota.setYear(command.getYear());
        //quota.getCurrentNumber()
        authority.addIssuerQuota(quota);
        repository.save(authority);
        return CommandResult.success();
    }
}

