package epermit.commands;

import an.awesome.pipelinr.Command;
import epermit.common.CommandResult;
import lombok.Data;

@Data
public class CreateAuthorityCommand implements Command<CommandResult> {
    private String code;

    private String uri;

    private String Name;
}
