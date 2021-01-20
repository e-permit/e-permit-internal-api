package permit.utils;

import java.io.FileInputStream;
import java.security.KeyStore;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWKSet;

import org.springframework.beans.factory.annotation.Value;

public class KeyUtils {

    @Value("")
    private String password;

    @Value("")
    private String jksFile;

    @Value("")
    private String kid;

    public ECKey getCurrentKey() throws Exception {
        KeyStore keyStore = KeyStore.getInstance( KeyStore.getDefaultType());
        keyStore.load(new FileInputStream(jksFile), password.toCharArray());
        //Key key = keyStore.getKey(kid, password.toCharArray());
        return ECKey.load(keyStore, kid, password.toCharArray());
    }

    public JWKSet getJwkSet() throws Exception{
        KeyStore keyStore = KeyStore.getInstance( KeyStore.getDefaultType());
        keyStore.load(new FileInputStream(jksFile), password.toCharArray());
        JWKSet jwkSet = JWKSet.load(keyStore, null);
        return jwkSet;
    }

}
