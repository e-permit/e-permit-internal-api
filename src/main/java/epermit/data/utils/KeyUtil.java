package epermit.data.utils;

import java.time.OffsetDateTime;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.gen.ECKeyGenerator;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Component;
import epermit.config.EPermitProperties;
import epermit.data.entities.Key;
import epermit.data.repositories.KeyRepository;
import lombok.SneakyThrows;

@Component
public class KeyUtil {
    private EPermitProperties props;
    private KeyRepository repository;

    public KeyUtil(EPermitProperties props, KeyRepository repository) {
        this.props = props;
        this.repository = repository;
    }

    @SneakyThrows
    public Key create(String kid) {
        final String salt = KeyGenerators.string().generateKey();
        TextEncryptor encryptor = Encryptors.text(props.getKeyPassword(), salt);
        ECKey key = new ECKeyGenerator(Curve.P_256).keyUse(KeyUse.SIGNATURE).keyID(kid).generate();
        String jwk = encryptor.encrypt(key.toJSONString());
        Key k = new Key();
        k.setKid(kid);
        k.setCreatedAt(OffsetDateTime.now());
        k.setEnabled(false);
        k.setSalt(salt);
        k.setContent(jwk);
        return k;
    }

    @SneakyThrows
    public ECKey getKey() {
        Key k = repository.findOneByEnabledTrue().get();
        TextEncryptor decryptor = Encryptors.text(props.getKeyPassword(), k.getSalt());
        ECKey key = ECKey.parse(decryptor.decrypt(k.getContent()));
        return key;
    }

    /*@SneakyThrows
    public String createJwt(String aud, Map<String, Object> claims) {
        ECKey key = getKey();
        Date iat = new Date();
        JWTClaimsSet.Builder claimsSet = new JWTClaimsSet.Builder()
                .issuer(props.getIssuer().getCode()).issueTime(iat).audience(aud);
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256).keyID(key.getKeyID()).build();
        claims.forEach((k, v) -> {
            claimsSet.claim(k, v);
        });
        SignedJWT signedJWT = new SignedJWT(header, claimsSet.build());
        JWSSigner signer = new ECDSASigner(key);
        signedJWT.sign(signer);
        String jwt = signedJWT.serialize();
        return jwt;
    }*/
}
