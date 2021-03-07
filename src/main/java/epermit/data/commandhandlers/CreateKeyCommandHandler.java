package epermit.data.commandhandlers;

import javax.transaction.Transactional;
import an.awesome.pipelinr.Command;
import epermit.commands.CreateKeyCommand;
import epermit.common.CommandResult;
import epermit.data.entities.Key;
import epermit.data.repositories.KeyRepository;
import epermit.data.utils.KeyUtil;
import lombok.SneakyThrows;

public class CreateKeyCommandHandler implements Command.Handler<CreateKeyCommand, CommandResult> {
    private final KeyRepository repository;
    private final KeyUtil util;

    public CreateKeyCommandHandler(KeyRepository repository, KeyUtil util){
        this.repository = repository;
        this.util = util;
    }

    @Override
    @Transactional
    @SneakyThrows
    public CommandResult handle(CreateKeyCommand cmd) {
        Key k = util.create(cmd.getKid());
        repository.save(k);
        return CommandResult.success();
    }

}
