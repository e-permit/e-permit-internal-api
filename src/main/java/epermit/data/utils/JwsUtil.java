package epermit.data.utils;

import com.google.gson.Gson;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.jwk.ECKey;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;
import epermit.common.JwsValidationResult;
import epermit.config.EPermitProperties;
import epermit.data.entities.Key;
import epermit.data.repositories.AuthorityRepository;
import epermit.data.repositories.KeyRepository;
import lombok.SneakyThrows;

@Component
public class JwsUtil {
    private EPermitProperties props;
    private KeyRepository repository;
    private AuthorityRepository authorityRepository;

    public JwsUtil(EPermitProperties props, KeyRepository repository,
            AuthorityRepository authorityRepository) {
        this.props = props;
        this.repository = repository;
        this.authorityRepository = authorityRepository;
    }

    @SneakyThrows
    public <T> String createJws(T payloadObj) {
        Gson gson = new Gson();
        Key k = repository.findOneByEnabledTrue().get();
        TextEncryptor decryptor = Encryptors.text(props.getKeyPassword(), k.getSalt());
        ECKey key = ECKey.parse(decryptor.decrypt(k.getContent()));
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256).keyID(key.getKeyID()).build();
        Payload payload = new Payload(gson.toJson(payloadObj));
        JWSObject jwsObject = new JWSObject(header, payload);
        JWSSigner signer = new ECDSASigner(key);
        jwsObject.sign(signer);
        return jwsObject.serialize();
    }

    @SneakyThrows
    public  JwsValidationResult validateJws(String jws) {
        String aud = getClaim(jws, "aud");
        String iss = getClaim(jws, "iss");
        return JwsValidationResult.success();
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <T> T getClaim(String jws, String key) {
        JWSObject jwsObject = JWSObject.parse(jws);
        return (T) jwsObject.getPayload().toJSONObject().get(key);
    }

}
