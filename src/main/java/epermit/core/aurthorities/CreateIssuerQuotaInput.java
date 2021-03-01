package epermit.core.aurthorities;

import lombok.Data;

@Data
public class CreateIssuerQuotaInput {
    private int year;

    private int permitType;

    private int startId;

    private int endId;
}