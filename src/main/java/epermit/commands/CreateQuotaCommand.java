package epermit.commands;

import an.awesome.pipelinr.Command;
import epermit.common.CommandResult;
import lombok.Data;

@Data
public class CreateQuotaCommand implements Command<CommandResult> {
    private int year;

    private int permitType;

    private int startId;

    private int endId;
}
