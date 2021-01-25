package epermit.core.aurthorities;

import java.util.Date;
import lombok.Data;

@Data
public class AuthorityKey {
  
    private Long id;

    private int kid;

    private Boolean isActive;
    
    private Date createdAt;

    private Date disabledAt;

    private String content;
}
