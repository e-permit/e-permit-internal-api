package epermit.commands;
import an.awesome.pipelinr.Command;
import epermit.common.CommandResult;
import lombok.Data;

@Data
public class RevokeQuotaCommand implements Command<CommandResult> {
    private Long quotaId;
}
