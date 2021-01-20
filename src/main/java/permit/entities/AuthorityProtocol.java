package permit.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class AuthorityProtocol {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "permit_type", nullable = false)
    private int permitType;

    @Column(name = "direction", nullable = false)
    private int direction;

    @Column(name = "start_id", nullable = false)
    private int startId;

    @Column(name = "end_id", nullable = false)
    private int endId;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "disabled_at", nullable = true)
    private Date disabledAt;

    @ManyToOne
    @JoinColumn(name = "authority_id") 
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Authority authority;
}