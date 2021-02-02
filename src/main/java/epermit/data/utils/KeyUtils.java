package epermit.data.utils;

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
import lombok.SneakyThrows;

@Component
public class KeyUtils {
    private EPermitProperties props;
    public KeyUtils(EPermitProperties props) {
        this.props = props;
    }

    @SneakyThrows
    public Pair<String, String> Create(String kid) {
        final String salt = KeyGenerators.string().generateKey();
        TextEncryptor encryptor = Encryptors.text(props.getKeyPassword(), salt);
        ECKey key = new ECKeyGenerator(Curve.P_256).keyUse(KeyUse.SIGNATURE).keyID(kid).generate();
        String jwk = encryptor.encrypt(key.toJSONString());
        return Pair.of(salt, jwk);
    }

    @SneakyThrows
    public ECKey GetKey(String salt,  String jwk){
        TextEncryptor decryptor = Encryptors.text(props.getKeyPassword(), salt);
        ECKey key = ECKey.parse(decryptor.decrypt(jwk));
        return key;
    }
}
