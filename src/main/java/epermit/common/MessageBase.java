package epermit.common;

import lombok.Data;


@Data
public class MessageBase {
    private String issuer;
    private String audience;
    private Long issuedAt;
    private MessageType messageType;
    private String messageId;
}
