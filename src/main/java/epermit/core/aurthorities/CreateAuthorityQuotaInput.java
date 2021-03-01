package epermit.core.aurthorities;

import lombok.Data;

@Data
public class CreateAuthorityQuotaInput {
    private int year;

    private int permitType;

    private int startId;

    private int endId;
}
