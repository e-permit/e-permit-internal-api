package epermit.core.keys;

import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWKSet;

public interface KeyService {
    ECKey getCurrentKey()  throws Exception;

    JWKSet getJwkSet() throws Exception;
}
