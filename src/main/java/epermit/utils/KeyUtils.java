package epermit.utils;

import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.gen.ECKeyGenerator;

import org.springframework.data.util.Pair;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

import lombok.SneakyThrows;

public class KeyUtils {
    @SneakyThrows
    public static Pair<String, String> Create(String kid, String password) {
        final String salt = KeyGenerators.string().generateKey();
        TextEncryptor encryptor = Encryptors.text(password, salt);
        ECKey key = new ECKeyGenerator(Curve.P_256).keyUse(KeyUse.SIGNATURE).keyID(kid).generate();
        String jwk = encryptor.encrypt(key.toJSONString());
        return Pair.of(salt, jwk);
    }

    @SneakyThrows
    public static ECKey GetKey(String password, String salt,  String jwk){
        TextEncryptor decryptor = Encryptors.text(password, salt);
        ECKey key = ECKey.parse(decryptor.decrypt(jwk));
        return key;
    }
}
