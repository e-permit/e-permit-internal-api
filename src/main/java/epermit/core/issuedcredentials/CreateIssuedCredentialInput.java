package epermit.core.issuedcredentials;

import java.util.HashMap;
import java.util.Map;
import epermit.common.PermitType;
import lombok.Data;

@Data
public class CreateIssuedCredentialInput {
    private String issuedFor;

    private PermitType permitType;

    private int permitYear;

    private String plateNumber;
    
    private String companyName;

    private Map<String, Object> claims = new HashMap<>();
}
