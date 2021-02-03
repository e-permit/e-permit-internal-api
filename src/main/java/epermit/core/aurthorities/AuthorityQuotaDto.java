package epermit.core.aurthorities;

import java.util.Date;

import lombok.Data;

@Data
public class AuthorityQuotaDto {

    private int id;

    private int year;

    private int permitType;

    private int direction;

    private int startId;

    private int endId;

    private Boolean isActive;
    
    private Date createdAt;

    private Date disabledAt;
}
