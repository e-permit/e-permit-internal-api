package epermit.messages;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import an.awesome.pipelinr.Command;
import epermit.common.MessageBase;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

@Getter
@Builder(toBuilder = true)
public class CreatePermitMessage extends MessageBase implements Command<String> {
    private String serialNumber;
    private String permitType;
    private int permitYear;
    private int permitId;
    private String companyName;
    private String plateNumber;
    @Singular
    private Map<String, Object> claims;
}
