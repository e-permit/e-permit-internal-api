package epermit.messages;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import an.awesome.pipelinr.Command;
import epermit.common.MessageResult;
import epermit.common.MessageType;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class CreatePermitMessage extends MessageBase implements Command<MessageResult> {
    private String serialNumber;
    private String permitType;
    private int permitYear;
    private int permitId;
    private String companyName;
    private String plateNumber;
    @Singular
    private Map<String, Object> claims;

    public static CreatePermitMessageBuilder<?, ?> init(String issuer, String audience) {
        return CreatePermitMessage.builder().issuer(issuer).audience(audience)
                .issuedAt(OffsetDateTime.now(ZoneOffset.UTC).toEpochSecond())
                .messageType(MessageType.CREATE_PERMIT);
    }
}
