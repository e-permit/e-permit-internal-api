package epermit.commands;

import an.awesome.pipelinr.Command;
import epermit.common.CommandResult;
import lombok.Data;

@Data
public class RevokePermitCommand implements Command<CommandResult> {
    private Long id;

    private String comment;
}
