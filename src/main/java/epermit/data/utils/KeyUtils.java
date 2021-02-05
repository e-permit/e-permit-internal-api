package epermit.data.utils;

import java.util.Date;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.gen.ECKeyGenerator;

import org.springframework.data.util.Pair;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Component;
import epermit.config.EPermitProperties;
import epermit.data.entities.Key;
import epermit.data.repositories.KeyRepository;
import lombok.SneakyThrows;

@Component
public class KeyUtils {
    private EPermitProperties props;
    private KeyRepository repository;
    public KeyUtils(EPermitProperties props, KeyRepository repository) {
        this.props = props;
        this.repository = repository;
    }

    @SneakyThrows
    public Key Create(String kid) {
        final String salt = KeyGenerators.string().generateKey();
        TextEncryptor encryptor = Encryptors.text(props.getKeyPassword(), salt);
        ECKey key = new ECKeyGenerator(Curve.P_256).keyUse(KeyUse.SIGNATURE).keyID(kid).generate();
        String jwk = encryptor.encrypt(key.toJSONString());
        Key k = new Key();
        k.setKid(kid);
        k.setCreatedAt(new Date());
        k.setEnabled(false);
        k.setSalt(salt);
        k.setContent(jwk);
        return k;
    }

    @SneakyThrows
    public ECKey GetKey(){
        Key k = repository.findOneByEnabledTrue().get();
        TextEncryptor decryptor = Encryptors.text(props.getKeyPassword(), k.getSalt());
        ECKey key = ECKey.parse(decryptor.decrypt(k.getContent()));
        return key;
    }
}
