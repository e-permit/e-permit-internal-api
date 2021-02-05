package epermit.data.utils;

import java.util.Date;
import java.util.Optional;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Component;
import epermit.core.issuedcredentials.IssuedCredentialService;
import epermit.data.entities.Authority;
import epermit.data.entities.IssuedCredential;
import epermit.data.repositories.AuthorityRepository;
import epermit.data.repositories.IssuedCredentialRepository;

@Component
public class CredentialUtils {

    private final AuthorityRepository authorityRepository;
    private final IssuedCredentialRepository issuedCredentialRepository;
    private final KeyUtils keyUtils;

    public CredentialUtils(AuthorityRepository authorityRepository,
            IssuedCredentialRepository issuedCredentialRepository, KeyUtils keyUtils) {
        this.authorityRepository = authorityRepository;
        this.keyUtils = keyUtils;
        this.issuedCredentialRepository = issuedCredentialRepository;
    }

    public Integer GetPermitId(String aud, int py, int pt) {
        Authority authority = authorityRepository.findByCode(aud);
        Optional<IssuedCredential> lastCr = issuedCredentialRepository.findFirstByRevokedTrue();
        authority.getQuotas().stream()
                .filter(x -> x.getYear() == py && x.getPermitType() == pt && x.getDirection() == 1);

        return null;
    }

    public String CreatePermitQrCode() {
        return "";
    }

    public String CreatePermitJws() {
        return "";
    }

    public String CreatePermitRevokeJws() {
        return "";
    }

    public String CreatePermitUsedJws() {
        return "";
    }

    public Boolean ValidatePermitQrCode() {
        return false;
    }

    public Boolean ValidatePermitJws() {
        return false;
    }

    public Boolean ValidatePermitRevokeJws() {
        return false;
    }

    public Boolean ValidatePermitUsedJws() {
        return false;
    }

    public Boolean ValidatePermitId() {
        return false;
    }

    
    /*
     * private ECKey ecKey;
     * 
     * private String issuer;
     * 
     * public CredentialUtils(ECKey ecKey, String issuer) { this.ecKey = ecKey; this.issuer =
     * issuer; }
     * 
     * public String createJws(CreatePermitInput input) throws Exception { JWSSigner signer = new
     * ECDSASigner(ecKey); Date iat = new Date(); Date exp = new Date(new Date().getTime() + 60 * 60
     * * 1000); JWTClaimsSet.Builder claimsSet = new
     * JWTClaimsSet.Builder().subject(input.getSub()).issuer(issuer)
     * .expirationTime(exp).issueTime(iat).audience(input.getAud()); claimsSet.claim("pid",
     * input.getPid()); claimsSet.claim("py", input.getPy()); claimsSet.claim("pt", input.getPt());
     * JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256).keyID(ecKey.getKeyID()).build();
     * SignedJWT signedJWT = new SignedJWT(header, claimsSet.build()); signedJWT.sign(signer);
     * String jwt = signedJWT.serialize(); return jwt; }
     */
}
