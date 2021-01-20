package permit.dtos;

import lombok.Data;

@Data
public class CreatePermitInput {
    private String aud;
    private String sub;
    private String cid;
    private String pt;
    private String pid;
    private String py;
    private int prid;
}
