package epermit.unittests.utils;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.security.interfaces.ECPublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.jwk.ECKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import epermit.config.EPermitProperties;
import epermit.config.EPermitProperties.Issuer;
import epermit.data.entities.Key;
import epermit.data.repositories.KeyRepository;
import epermit.data.utils.KeyUtil;
import lombok.SneakyThrows;

@ExtendWith(MockitoExtension.class)
public class KeyUtilTest {
    @Mock
    EPermitProperties properties;

    @Mock
    KeyRepository repository;

    @Test
    void keyShouldBeCreatedWhenSaltAndPasswordIsCorrect() {
        when(properties.getKeyPassword()).thenReturn("123456");
        KeyUtil utils = new KeyUtil(properties, repository);
        Key key = utils.create("1");
        Assertions.assertNotNull(key.getSalt());
    }

    @Test
    void keyShouldNotBeCreatedWhenPasswordIsIncorrect() {
        when(properties.getKeyPassword()).thenReturn("123456");
        Assertions.assertThrows(IllegalStateException.class, () -> {
            KeyUtil utils = new KeyUtil(properties, repository);
            Key k = utils.create("1");
            when(repository.findOneByEnabledTrue()).thenReturn(Optional.of(k));
            when(properties.getKeyPassword()).thenReturn("1234567");
            ECKey key = utils.getKey();
        });
    }

    @Test
    @SneakyThrows
    void createJwsShouldWork() {
        KeyUtil utils = new KeyUtil(properties, repository);
        when(properties.getKeyPassword()).thenReturn("123456");
        Issuer issuer = new Issuer();
        issuer.setCode("TR");
        when(properties.getIssuer()).thenReturn(issuer);
        Key k = utils.create("1");
        when(repository.findOneByEnabledTrue()).thenReturn(Optional.of(k));
        Map<String, Object> claims = new HashMap<>();
        String jws = utils.createJwt("UA", claims);
        JWSObject jwsObject = JWSObject.parse(jws);
        Map<String, Object> resolvedClaims = jwsObject.getPayload().toJSONObject();
        ECPublicKey ecPublicKey = ECKey.parse(utils.getKey().toJSONString()).toECPublicKey();
        JWSVerifier verifier = new ECDSAVerifier(ecPublicKey);
        assertEquals(true, jwsObject.verify(verifier));
        assertEquals("TR", resolvedClaims.get("iss").toString());
        assertEquals("UA", resolvedClaims.get("aud").toString());
    }
}
