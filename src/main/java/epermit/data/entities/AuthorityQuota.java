package epermit.data.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // JPA
@Entity
@Table(name = "authority_quotas")
public class AuthorityQuota {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "permit_type", nullable = false)
    private int permitType;

    @Column(name = "vehicle_owner", nullable = false)
    private boolean vehicleOwner;

    @Column(name = "start_number", nullable = false)
    private int startNumber;

    @Column(name = "end_number", nullable = false)
    private int endNumber;

    @Column(name = "current_number", nullable = false)
    private int currentNumber;
    
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "authority_id") 
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Authority authority;
}
