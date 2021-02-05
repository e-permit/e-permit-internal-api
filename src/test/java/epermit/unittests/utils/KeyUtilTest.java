package epermit.unittests.utils;


import static org.mockito.Mockito.when;
import com.nimbusds.jose.jwk.ECKey;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;
import epermit.config.EPermitProperties;
import epermit.data.entities.Key;
import epermit.data.repositories.KeyRepository;
import epermit.data.utils.KeyUtils;

@ExtendWith(MockitoExtension.class)
public class KeyUtilTest {
    @Mock
    EPermitProperties properties;

    @Mock
    KeyRepository repository;
    @Test
    void keyShouldBeCreatedWhenSaltAndPasswordIsCorrect() {
        when(properties.getKeyPassword()).thenReturn("123456");
        KeyUtils utils = new KeyUtils(properties, repository);
        Key key = utils.Create("1");
        Assertions.assertNotNull(key.getSalt());
    }

    @Test
    void keyShouldNotBeCreatedWhenPasswordIsIncorrect() {
        when(properties.getKeyPassword()).thenReturn("123456");
        Assertions.assertThrows(IllegalStateException.class, () -> {
            KeyUtils utils = new KeyUtils(properties, repository);
            Key k = utils.Create("1");
            when(repository.findOneByEnabledTrue().get()).thenReturn(k);
            when(properties.getKeyPassword()).thenReturn("1234567");
            ECKey key = utils.GetKey();
        });
    }

    @Test
    void keyShouldNotBeCreatedWhenSaltIsIncorrect() {
        when(properties.getKeyPassword()).thenReturn("123456");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            KeyUtils utils = new KeyUtils(properties, repository);
            Key k = utils.Create("1");
            k.setSalt("123");
            when(repository.findOneByEnabledTrue().get()).thenReturn(k);
            ECKey key = utils.GetKey();
        });
    }
}
