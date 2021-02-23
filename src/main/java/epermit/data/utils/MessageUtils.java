package epermit.data.utils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jose.jwk.*;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.json.JSONArray;
import org.json.JSONObject;
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
        Authority authority = authorityRepository.findByCode(aud);
        restTemplate.postForEntity(authority.getUri(), request, Boolean.class);
        return true;
    }

    public void validateCreateMessage(String jws) {
        // get aud from payload
        // get authority
        // 
    }

    public void validateRevokeMessage(String jws) {
        // get aud from payload
        // get authority
        // 
    }

    public void validateFeedbackMessage(String jws) {
        JWSObject jwsObject = JWSObject.parse(jws);
        JWSHeader header = jwsObject.getHeader();
        Payload payload = jwsObject.getPayload();
        String iss = payload.toJSONObject().get("iss").toString();
        String pmt = payload.toJSONObject().get("pmt").toString();
        Authority authority = authorityRepository.findByCode(iss);
                List<AuthorityKey> keys = authority.getKeys();
                for (int j = 0, size = keys.size(); j < size; j++) {
                    JSONObject b = keys.getJSONObject(j);
                    String kid = b.getString("kid");
                    if (kid.equals(header.getKeyID())) {
                        jwkStr = b.toString();
                    }
                }
        // payload.toJSONObject().getAsString("iss");
        ECPublicKey ecPublicKey = ECKey.parse(jwkStr).toECPublicKey();
        JWSVerifier verifier = new ECDSAVerifier(ecPublicKey);
        Boolean r = jwsObject.verify(verifier);
        return r.toString();
        // get aud from payload
        // get authority
        // 
    }

    public Boolean validatePermitId(String permitId) {
        return false;
    }
}
