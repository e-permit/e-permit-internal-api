package epermit.core.aurthorities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Authority {
  private Long id;

  private String code;

  private String name;

  private String permitUri;

  private String claimsRule;

  private Boolean isActive;

  private Date createdAt;

  private List<AuthorityKey> keys = new ArrayList<>();

  private List<AuthorityQuota> quotas = new ArrayList<>();

}
