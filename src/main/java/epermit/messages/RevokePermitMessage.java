package epermit.messages;

import an.awesome.pipelinr.Command;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RevokePermitMessage implements Command<String> {
    private String serialNumber;
}
