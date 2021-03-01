package epermit.core.aurthorities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class AuthorityDto {
  private int id;

  private String code;

  private String name;

  private String uri;

  private String claimsRule;

  private Date createdAt;

  private List<AuthorityKeyDto> keys = new ArrayList<>();

  private List<AuthorityQuotaDto> authorityQuotas = new ArrayList<>();

  private List<IssuerQuotaDto> issuerQuotas = new ArrayList<>();
}
