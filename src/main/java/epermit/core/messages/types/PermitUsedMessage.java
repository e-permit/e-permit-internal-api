package epermit.core.messages.types;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PermitUsedMessage {
    private String messageType;
    private String serialNumber;
}
