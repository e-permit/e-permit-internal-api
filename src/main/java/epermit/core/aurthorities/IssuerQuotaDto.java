package epermit.core.aurthorities;

import java.util.Date;

import lombok.Data;

@Data
public class IssuerQuotaDto {

    private int id;

    private int year;

    private int permitType;

    private int startId;

    private int currentId;

    private int endId;

    private Boolean isActive;
    
    private Date createdAt;

    private Date disabledAt;
}
