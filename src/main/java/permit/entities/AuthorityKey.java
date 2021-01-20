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
public class AuthorityKey {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "kid", nullable = false)
    private int kid;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "disabled_at", nullable = true)
    private Date disabledAt;

    @Column(name = "content", nullable = false)
    private String content;
    
    @ManyToOne
    @JoinColumn(name = "authority_id")
    @EqualsAndHashCode.Exclude
    @JsonIgnore // Avoid infinite loops
    private Authority authority;
}
