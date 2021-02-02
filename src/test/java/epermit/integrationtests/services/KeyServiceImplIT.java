package epermit.integrationtests.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import epermit.common.CustomPostgresContainer;
import epermit.data.repositories.KeyRepository;
import epermit.data.services.KeyServiceImpl;
import epermit.data.utils.KeyUtils;


@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
public class KeyServiceImplIT {

    @Container
    public static PostgreSQLContainer postgreSQLContainer = CustomPostgresContainer.getInstance();

    @Autowired
    private KeyServiceImpl keyServiceImpl;

    /*@Autowired
    private KeyRepository keyRepository;

    @Autowired
    private KeyUtils keyUtils;*/

    @Test
    public void greetingShouldReturnDefaultMessage() {   
        keyServiceImpl.CreateKey("1");
        assertEquals(keyServiceImpl.getKeys().size(), 1); 
        //keyServiceImpl.CreateKey("2");   
    }
}


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
        //keyRepository.deleteById((long)2);
        // SecretKeySpec skey = new SecretKeySpec("keyb".getBytes(), "AES");

        //System.out.println(keyServiceImpl.getCurrentKey().toJSONString());
