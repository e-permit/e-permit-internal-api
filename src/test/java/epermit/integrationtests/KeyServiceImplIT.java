package epermit.integrationtests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import epermit.data.entities.Key;
import epermit.data.repositories.KeyRepository;
import epermit.data.services.KeyServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import javax.crypto.spec.SecretKeySpec;

import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.gen.ECKeyGenerator;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
public class KeyServiceImplIT {

    @Container
    public static PostgreSQLContainer postgreSQLContainer = CustomPostgresContainer.getInstance();

    @Autowired
    private KeyServiceImpl keyServiceImpl;

    @Autowired
    private KeyRepository keyRepository;

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        /*ECKey jwk = new ECKeyGenerator(Curve.P_256).keyUse(KeyUse.SIGNATURE) // indicate the intended use of the key
                .keyID("1") // give the key a unique ID
                .generate();
        Key k = new Key();
        k.setKid("1");
        k.setCreatedAt(new Date());
        k.setDeleted(false);
        k.setContent(jwk.toJSONString());
        keyRepository.save(k);

        ECKey jwk2 = new ECKeyGenerator(Curve.P_256).keyUse(KeyUse.SIGNATURE) // indicate the intended use of the key
                .keyID("2") // give the key a unique ID
                .generate();
        Key k2 = new Key();
        k2.setKid("2");
        k2.setCreatedAt(new Date());
        k2.setDeleted(false);
        k2.setContent(jwk2.toJSONString());
        keyRepository.save(k2);*/
        keyServiceImpl.CreateKey("1");
        keyServiceImpl.CreateKey("2");
        //keyRepository.deleteById((long)2);
        // SecretKeySpec skey = new SecretKeySpec("keyb".getBytes(), "AES");

        //System.out.println(keyServiceImpl.getCurrentKey().toJSONString());
    }
}
