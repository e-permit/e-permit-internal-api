package epermit.core.aurthorities;

import lombok.Data;

@Data
public class CreateAuthorityInput {
    private String code;

    private String uri;

    private String Name;
}
