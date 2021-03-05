package epermit.common;

import lombok.Data;

@Data
public class PermitQrCode {
    private String iss;
    private String aud;
    private Long iat;
    private int pid;
    private int py;
    private int pt;
    private String sub;
    private String cn;
}
