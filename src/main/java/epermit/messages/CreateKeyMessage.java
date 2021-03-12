package epermit.messages;

import an.awesome.pipelinr.Command;
import epermit.common.MessageBase;
import epermit.common.MessageHandleResult;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class CreateKeyMessage extends MessageBase implements Command<MessageHandleResult> {
    private String keyId;
    private String jwk;
}
