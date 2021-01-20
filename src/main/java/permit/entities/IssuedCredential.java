package permit.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
public class IssuedCredential {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "hash", nullable = false)
    private String hash;

    @Column(name = "jws", nullable = false)
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

    @Column(name = "sub", nullable = false)
    private String sub;

    @Column(name = "cid", nullable = false)
    private String cid;

    @Column(name = "claims", nullable = false)
    private String claims;

    @Column(name = "is_delivered", nullable = false)
    private boolean isDelivered;

    @Column(name = "delivered_at", nullable = true)
    private Date deliveredAt;

    @Column(name = "is_verified", nullable = false)
    private boolean isVerified;

    @Column(name = "verified_at", nullable = true)
    private Date verifiedAt;
}