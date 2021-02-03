package epermit.unittests.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.gen.ECKeyGenerator;
import com.nimbusds.jwt.SignedJWT;

import org.junit.jupiter.api.Test;

import epermit.data.utils.*;
import lombok.SneakyThrows;

class TokenUtilsTest {
    /*@Test
    @SneakyThrows
    void TokenShouldBeCreated() {
        ECKey ecJWK = new ECKeyGenerator(Curve.P_256).keyID("1").generate();
        TokenUtils utils = new TokenUtils(ecJWK, "tr");
        ApiClientValidationResult client = new ApiClientValidationResult();
        client.setClientId("abc");
        client.setExpiration(60);
        client.setScope("app");
        String jwt = utils.createToken(client);
        System.out.println(jwt);
        SignedJWT signedJWT = SignedJWT.parse(jwt);
        JWSVerifier verifier = new ECDSAVerifier(ecJWK.toPublicJWK());
        assertTrue(signedJWT.verify(verifier));
        assertEquals(client.getClientId(), signedJWT.getJWTClaimsSet().getSubject());
        assertEquals("tr", signedJWT.getJWTClaimsSet().getIssuer());
        assertTrue(new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime()));
    }*/
}
