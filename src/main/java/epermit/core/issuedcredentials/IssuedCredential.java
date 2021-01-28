package epermit.core.issuedcredentials;

import java.util.Date;

import lombok.Data;

@Data
public class IssuedCredential {
    private Long id;

    private String serialNumber;

    private String qrcode;
    
    private String jws;

    private int pid;

    private int pt;

    private int py;

    private long iat;

    private long exp;

    private String sub;

    private String cid;

    private String claims;

    private boolean isUsed;

    private Date usedAt;

    private boolean revoked;

    private Date revokedAt;
}
