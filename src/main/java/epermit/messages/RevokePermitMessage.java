package epermit.messages;

import an.awesome.pipelinr.Command;
import epermit.common.MessageHandleResult;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RevokePermitMessage implements Command<MessageHandleResult> {
    private String serialNumber;
}
