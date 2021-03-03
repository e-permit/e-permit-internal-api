package epermit.messages;

import epermit.common.MessageType;
import lombok.Getter;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder(toBuilder = true)
public class MessageBase {
    private String issuer;
    private String audience;
    private Long issuedAt;
    private MessageType messageType;
}
