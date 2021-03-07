package epermit.common;

import lombok.Data;


@Data
public class MessageBase {
    private String issuer;
    private String issuedFor;
    private Long createdAt;
    private MessageType messageType;
}
