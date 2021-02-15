package epermit.data.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import epermit.config.EPermitProperties;
import epermit.core.issuedcredentials.CreateIssuedCredentialInput;
import epermit.data.entities.Authority;
import epermit.data.entities.AuthorityQuota;
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
        Optional<IssuedCredential> revokedCred =
                issuedCredentialRepository.findFirstByRevokedTrue();
        if (revokedCred.isPresent()) {
            int nextPid = revokedCred.get().getPid();
            issuedCredentialRepository.delete(revokedCred.get());
            return nextPid;
        }

        Optional<AuthorityQuota> quotaResult =
                authority.getQuotas().stream().filter(x -> x.getYear() == py && x.isActive()
                        && x.getPermitType() == pt && x.isVehicleOwner()).findFirst();
        if (quotaResult.isPresent()) {
            AuthorityQuota quota = quotaResult.get();
            int nextPid = quota.getCurrentNumber() + 1;
            quota.setCurrentNumber(nextPid);
            if (quota.getCurrentNumber() == quota.getEndNumber()) {
                quota.setActive(false);
            }
            authorityRepository.save(authority);
            return nextPid;
        }
        return null;
    }

    @SneakyThrows
    public String createPermitQrCode(CreateIssuedCredentialInput input, int pid) {
        JWTClaimsSet.Builder claimsSet =
                new JWTClaimsSet.Builder().issuer(props.getIssuer().getCode())
                        .audience(input.getAud()).subject(input.getSub()).claim("py", input.getPy())
                        .claim("pt", input.getPt()).claim("cn", input.getCn()).claim("pid", pid);
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256)
                .keyID(keyUtils.GetKey().getKeyID()).build();
        SignedJWT signedJWT = new SignedJWT(header, claimsSet.build());
        JWSSigner signer = new ECDSASigner(keyUtils.GetKey());
        signedJWT.sign(signer);
        String jwt = signedJWT.serialize();
        return jwt;
    }

    @SneakyThrows
    public String createPermitJws(CreateIssuedCredentialInput input, int pid) {
        Instant iat = Instant.now();
        Instant exp = iat.plus(1, ChronoUnit.YEARS);
        JWTClaimsSet.Builder claimsSet =
                new JWTClaimsSet.Builder().issuer(props.getIssuer().getCode())
                        .expirationTime(Date.from(exp)).issueTime(Date.from(iat))
                        .audience(input.getAud()).subject(input.getSub()).claim("py", input.getPy())
                        .claim("pt", input.getPt()).claim("cn", input.getCn()).claim("pid", pid);
        input.getClaims().forEach((k, v) -> {
            claimsSet.claim(k, v);
        });
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256)
                .keyID(keyUtils.GetKey().getKeyID()).build();
        SignedJWT signedJWT = new SignedJWT(header, claimsSet.build());
        JWSSigner signer = new ECDSASigner(keyUtils.GetKey());
        signedJWT.sign(signer);
        String jwt = signedJWT.serialize();
        return jwt;
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
