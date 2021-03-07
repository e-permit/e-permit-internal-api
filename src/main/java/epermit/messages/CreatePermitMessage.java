package epermit.messages;

import java.util.Map;
import an.awesome.pipelinr.Command;
import epermit.common.MessageBase;
import epermit.common.PermitType;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

@Getter
@Builder(toBuilder = true)
public class CreatePermitMessage extends MessageBase implements Command<String> {
    private String serialNumber;
    private PermitType permitType;
    private int permitYear;
    private int permitId;
    private String IssuedAt;
    private String ExpireAt;
    private String companyName;
    private String plateNumber;
    @Singular
    private Map<String, Object> claims;
}
