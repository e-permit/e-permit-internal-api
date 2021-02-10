package epermit.core.issuedcredentials;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class CreateIssuedCredentialInput {
    private String aud;

    private int pt;

    private int py;

    private String sub;
    
    private String cn;

    //private String cid;

    //private String res;

    private Map<String, String> claims = new HashMap<>();
}
