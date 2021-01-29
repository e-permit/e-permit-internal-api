package epermit.data.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // JPA
@Entity
@Table(name = "keys")
//@SQLDelete(sql = "UPDATE keys SET deleted = true WHERE id = ?")
//@Where(clause = "deleted = false")
public class Key {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "kid", nullable = false)
    private String kid;
    
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "salt", nullable = false)
    private String salt;

    @Column(name = "content", nullable = false, length=4000)
    private String content;
}
