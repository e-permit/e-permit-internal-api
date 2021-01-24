package epermit.utils;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Enumeration;

import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWKSet;
import org.springframework.beans.factory.annotation.Value;

public class KeyUtils {
    @Value("")
    private String password;

    @Value("")
    private String jksFile;

    public ECKey getCurrentKey() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(new FileInputStream(jksFile), password.toCharArray());
        Long lastKID = null;
        Enumeration<String> kidList = keyStore.aliases();
        while (kidList.hasMoreElements()) {
            Long kid = Long.parseLong(kidList.nextElement());
            if (lastKID == null || lastKID < kid) {
                lastKID = kid;
            }
        }
        return ECKey.load(keyStore, Long.toString(lastKID) , password.toCharArray());
    }

    public JWKSet getJwkSet() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(new FileInputStream(jksFile), password.toCharArray());
        JWKSet jwkSet = JWKSet.load(keyStore, null);
        return jwkSet;
    }

}
