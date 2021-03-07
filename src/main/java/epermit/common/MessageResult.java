package epermit.common;

import lombok.Data;

@Data
public class MessageResult {
    private String issuer;

    private String audience;

    private Long issuedAt;

    private String messageId;

    private String resultCode;
}
