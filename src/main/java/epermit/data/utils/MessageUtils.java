package epermit.data.utils;

import java.security.interfaces.ECPublicKey;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jose.jwk.*;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import epermit.config.EPermitProperties;
import epermit.data.entities.Authority;
import epermit.data.entities.AuthorityKey;
import epermit.data.repositories.AuthorityRepository;
import lombok.SneakyThrows;

public class MessageUtils {

    private final EPermitProperties props;
    private final RestTemplate restTemplate;
    private final KeyUtils keyUtils;
    private final AuthorityRepository authorityRepository;

    public MessageUtils(EPermitProperties props, RestTemplate restTemplate, KeyUtils keyUtils,
            AuthorityRepository authorityRepository) {
        this.keyUtils = keyUtils;
        this.props = props;
        this.restTemplate = restTemplate;
        this.authorityRepository = authorityRepository;
    }

    @SneakyThrows
    public String createMessageJws(String aud, Map<String, String> claims) {
        // LocalDateTime.now(ZoneOffset.UTC).plusYears(1).plusMonths(1);
        Date iat = new Date();
        Date exp = new Date(new Date().getTime() + 60 * 60 * 1000);
        JWTClaimsSet.Builder claimsSet =
                new JWTClaimsSet.Builder().issuer(props.getIssuer().getCode()).expirationTime(exp)
                        .issueTime(iat).audience(aud);
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256)
                .keyID(keyUtils.GetKey().getKeyID()).build();
        claims.forEach((k, v) -> {
            claimsSet.claim(k, v);
        });
        SignedJWT signedJWT = new SignedJWT(header, claimsSet.build());
        JWSSigner signer = new ECDSASigner(keyUtils.GetKey());
        signedJWT.sign(signer);
        String jwt = signedJWT.serialize();
        return jwt;
    }

    @SneakyThrows
    public boolean sendMesaage(String aud, String jwt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(jwt, headers);
        Optional<Authority> authority = authorityRepository.findByCode(aud);
        restTemplate.postForEntity(authority.get().getUri(), request, Boolean.class);
        return true;
    }

    @SneakyThrows
    public JwsValidationResult validateMessageJws(String jws) {
        JWSObject jwsObject = JWSObject.parse(jws);
        JWSHeader header = jwsObject.getHeader();
        Payload payload = jwsObject.getPayload();
        String iss = payload.toJSONObject().get("iss").toString();
        //String pmt = payload.toJSONObject().get("pmt").toString();
        Optional<Authority> authority = authorityRepository.findByCode(iss);
        if(!authority.isPresent()){
           return JwsValidationResult.fail("ISS_NOTFOUND", "The issuer is not found");
        }
        Optional<AuthorityKey> key = authority.get().getKeys().stream()
                .filter(x -> x.getKid().equals(header.getKeyID())).findFirst();

        ECPublicKey ecPublicKey = ECKey.parse(key.get().getContent()).toECPublicKey();
        JWSVerifier verifier = new ECDSAVerifier(ecPublicKey);
        Boolean r = jwsObject.verify(verifier);
        if(!r){
            return JwsValidationResult.fail("INVALID_SIG", "The signature is invalid");
        }
        return JwsValidationResult.success(payload.toJSONObject());
    }

    /*public Integer getPermitId(String aud, int py, int pt) {
        Optional<Authority> authority = authorityRepository.findByCode(aud);
        Optional<IssuedCredential> revokedCred =
                issuedCredentialRepository.findFirstByRevokedTrue();
        if (revokedCred.isPresent()) {
            int nextPid = revokedCred.get().getPid();
            issuedCredentialRepository.delete(revokedCred.get());
            return nextPid;
        }

        Optional<AuthorityQuota> quotaResult =
                authority.get().getQuotas().stream().filter(x -> x.getYear() == py && x.isActive()
                        && x.getPermitType() == pt && x.isVehicleOwner()).findFirst();
        if (quotaResult.isPresent()) {
            AuthorityQuota quota = quotaResult.get();
            int nextPid = quota.getCurrentNumber() + 1;
            quota.setCurrentNumber(nextPid);
            if (quota.getCurrentNumber() == quota.getEndNumber()) {
                quota.setActive(false);
            }
            authorityRepository.save(authority.get());
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
    }*/

    public Boolean validatePermitId(String permitId) {
        return false;
    }
}
