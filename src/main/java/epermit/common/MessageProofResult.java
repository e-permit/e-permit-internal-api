package epermit.common;

import lombok.Data;

@Data
public class MessageProofResult {
    private boolean valid;
    private String issuer;
    private String messageId;
}
