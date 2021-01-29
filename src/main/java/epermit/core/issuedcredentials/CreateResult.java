package epermit.core.issuedcredentials;

import epermit.common.CommandResult;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class CreateResult extends CommandResult {
    private final IssuedCredential issued_credential;
}
