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
@Table(name = "credentials")
@SQLDelete(sql = "UPDATE issued_credentials SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Credential {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @Column(name = "qrcode", nullable = false, length=1000)
    private String qrcode;

    @Column(name = "jws", nullable = false, length=5000)
    private String jws;

    @Column(name = "pid", nullable = false)
    private int pid;

    @Column(name = "pt", nullable = false)
    private int pt;

    @Column(name = "py", nullable = false)
    private int py;

    @Column(name = "iat", nullable = false)
    private long iat;

    @Column(name = "exp", nullable = false)
    private long exp;

    @Column(name = "iss", nullable = false)
    private String iss;

    @Column(name = "sub", nullable = false)
    private String sub;

    @Column(name = "cid", nullable = false)
    private String cid;

    @Column(name = "claims", nullable = false, length=1000)
    private String claims;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "is_used", nullable = false)
    private boolean isUsed;

    @Column(name = "is_usage_delivered", nullable = false)
    private boolean isUsageDelivered;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;
}
