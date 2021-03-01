package epermit.core.issuedcredentials;

import java.util.Date;
import epermit.common.PermitType;
import lombok.Data;

@Data
public class IssuedCredentialDto {
    private Long id;

    private String serialNumber;

    private String qrcode;

    private int pid;

    private PermitType pt;

    private int py;

    private long iat;

    private long exp;

    private String sub;

    private String cn;

    private String claims;

    private boolean used;

    private Date usedAt;

    private boolean revoked;

    private Date revokedAt;
}
