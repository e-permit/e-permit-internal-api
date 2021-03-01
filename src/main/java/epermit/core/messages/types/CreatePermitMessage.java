package epermit.core.messages.types;

import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePermitMessage {
    private String messageType;
    private String serialNumber;
    private String permitType;
    private int permitYear;
    private int permitId;
    private String companyName;
    private String plateNumber;
    private Map<String, Object> claims;
}
