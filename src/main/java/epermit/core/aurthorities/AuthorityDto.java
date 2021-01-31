package epermit.core.aurthorities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class AuthorityDto {
  private Long id;

  private String code;

  private String name;

  private String permitUri;

  private String claimsRule;

  private Boolean isActive;

  private Date createdAt;

  private List<AuthorityKeyDto> keys = new ArrayList<>();

  private List<AuthorityQuotaDto> quotas = new ArrayList<>();

}
