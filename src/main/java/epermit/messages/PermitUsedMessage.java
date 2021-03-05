package epermit.messages;

import an.awesome.pipelinr.Command;
import epermit.common.MessageBase;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class PermitUsedMessage extends MessageBase implements Command<String> {
    private String serialNumber;
}
