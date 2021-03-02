package epermit.data.commandhandlers;

import org.springframework.stereotype.Component;
import an.awesome.pipelinr.Command;
import epermit.commands.CreateQuotaCommand;
import epermit.common.CommandResult;

@Component
public class CreateQuotaCommandHandler
        implements Command.Handler<CreateQuotaCommand, CommandResult> {

    @Override
    public CommandResult handle(CreateQuotaCommand command) {
        return CommandResult.success();
    }

}

