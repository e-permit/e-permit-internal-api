package epermit.messages;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RevokeKeyMessage {
    private String messageType;
    private String keyId;
}
