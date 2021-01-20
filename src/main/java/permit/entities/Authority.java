package permit.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // JPA
@Entity
@Table(name = "authorities")
public class Authority {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "code", nullable = false)
  private int code;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "permit_uri", nullable = false)
  private String permitUri;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive;

  @Column(name = "created_at", nullable = true)
  private Date createdAt;
  
  @OneToMany(cascade = CascadeType.ALL)
  @EqualsAndHashCode.Exclude
  private List<AuthorityKey> keys = new ArrayList<>();

  @JsonIgnore
  public void addKey(AuthorityKey key) {
    keys.add(key);
    key.setAuthority(this);
  }
 
}
