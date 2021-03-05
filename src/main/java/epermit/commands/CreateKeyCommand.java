package epermit.commands;
import an.awesome.pipelinr.Command;
import epermit.common.CommandResult;
import lombok.Data;

@Data
public class CreateKeyCommand implements Command<CommandResult> {
    private String kid;
}
