package epermit.utils;

import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.gen.ECKeyGenerator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

import lombok.SneakyThrows;

public class KeyUtils {

    private String password;

    public KeyUtils(@Value("${epermit.key.password}") String password) {
        this.password = password;
    }

    @SneakyThrows
    public Pair<String, String> Create(String kid) {
        final String salt = KeyGenerators.string().generateKey();
        TextEncryptor encryptor = Encryptors.text(password, salt);
        ECKey key = new ECKeyGenerator(Curve.P_256).keyUse(KeyUse.SIGNATURE).keyID(kid).generate();
        String jwk = encryptor.encrypt(key.toJSONString());
        return Pair.of(salt, jwk);
    }

    @SneakyThrows
    public ECKey GetKey(String salt,  String jwk){
        TextEncryptor decryptor = Encryptors.text(password, salt);
        ECKey key = ECKey.parse(decryptor.decrypt(jwk));
        return key;
    }
}
