package epermit.data.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit; 
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
import epermit.config.EPermitProperties;
import epermit.core.issuedcredentials.IssuedCredentialService;
import epermit.data.entities.Authority;
import epermit.data.entities.IssuedCredential;
import epermit.data.repositories.AuthorityRepository;
import epermit.data.repositories.IssuedCredentialRepository;
import lombok.SneakyThrows;

@Component
public class CredentialUtils {

    private final AuthorityRepository authorityRepository;
    private final IssuedCredentialRepository issuedCredentialRepository;
    private final KeyUtils keyUtils;
    private final EPermitProperties props;

    public CredentialUtils(AuthorityRepository authorityRepository,
            IssuedCredentialRepository issuedCredentialRepository, KeyUtils keyUtils,
            EPermitProperties props) {
        this.authorityRepository = authorityRepository;
        this.keyUtils = keyUtils;
        this.issuedCredentialRepository = issuedCredentialRepository;
        this.props = props;
    }

    public Integer getPermitId(String aud, int py, int pt) {
        Authority authority = authorityRepository.findByCode(aud);
        Optional<IssuedCredential> lastCr = issuedCredentialRepository.findFirstByRevokedTrue();
        authority.getQuotas().stream()
                .filter(x -> x.getYear() == py && x.getPermitType() == pt && x.getDirection() == 1);

        return null;
    }

    @SneakyThrows
    public String createToken(String aud) {
        //Instant.now().plus(1, ChronoUnit.YEARS);
        //Date d = Date.from(Instant.now());
        //LocalDateTime.now(ZoneOffset.UTC).plusYears(1).plusMonths(1);
        Date iat = new Date();
        Date exp = new Date(new Date().getTime() + 60 * 60 * 1000);
        JWTClaimsSet.Builder claimsSet = new JWTClaimsSet.Builder().issuer(props.getIssuer().getCode()).expirationTime(exp)
                .issueTime(iat).audience(aud);
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256)
                .keyID(keyUtils.GetKey().getKeyID()).build();
        SignedJWT signedJWT = new SignedJWT(header, claimsSet.build());
        JWSSigner signer = new ECDSASigner(keyUtils.GetKey());
        signedJWT.sign(signer);
        String jwt = signedJWT.serialize();
        return jwt;
    }

    public String createPermitQrCode() {
        return "";
    }

    public String createPermitJws() {
        return "";
    }

    public String createPermitRevokeJws() {
        return "";
    }

    public String createPermitUsedJws() {
        return "";
    }

    public Boolean validatePermitQrCode() {
        return false;
    }

    public Boolean validatePermitJws() {
        return false;
    }

    public Boolean validatePermitRevokeJws() {
        return false;
    }

    public Boolean validatePermitUsedJws() {
        return false;
    }

    public Boolean validatePermitId() {
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
