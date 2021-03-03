package epermit.messages;

import an.awesome.pipelinr.Command;
import epermit.common.MessageResult;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class PermitUsedMessage extends MessageBase implements Command<MessageResult> {
    private String serialNumber;
}
