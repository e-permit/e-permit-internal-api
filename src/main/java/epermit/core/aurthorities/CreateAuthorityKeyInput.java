package epermit.core.aurthorities;

import lombok.Data;

@Data
public class CreateAuthorityKeyInput {
    private String kid;

    private String jwk;
}
