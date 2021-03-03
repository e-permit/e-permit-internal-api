package epermit.messages;

import an.awesome.pipelinr.Command;
import epermit.common.MessageResult;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RevokePermitMessage implements Command<MessageResult> {
    private String serialNumber;
}
