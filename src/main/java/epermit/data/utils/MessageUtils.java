package epermit.data.utils;

import java.util.Date;
import java.util.Map;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import epermit.config.EPermitProperties;
import epermit.data.entities.Authority;
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
        Authority authority = authorityRepository.findByCode(aud);
        restTemplate.postForEntity(authority.getUri(), request, Boolean.class);
        return true;
    }

    public void validateCreateMessage(String jws) {

    }

    public void validateRevokeMessage(String jws) {

    }

    public void validateFeedbackMessage(String jws) {

    }

    public void validateQrCode(String jws) {

    }

    public Boolean validatePermitId() {
        return false;
    }
}
