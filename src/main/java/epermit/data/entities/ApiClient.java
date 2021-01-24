package epermit.data.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
public class ApiClient {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "client_id", nullable = false)
    private String clientId;

    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Column(name = "client_secret", nullable = false)
    private String clientSecret;

    // admin  issuer  verifier  read
    @Column(name = "scope", nullable = false)
    private String scope;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "expiration", nullable = false)
    private int expiration;
}
