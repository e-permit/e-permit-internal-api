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
import epermit.data.utils.KeyUtils;

@ExtendWith(MockitoExtension.class)
public class KeyUtilTest {
    @Mock
    EPermitProperties properties;

    @Test
    void keyShouldBeCreatedWhenSaltAndPasswordIsCorrect() {
        when(properties.getKeyPassword()).thenReturn("123456");
        KeyUtils utils = new KeyUtils(properties);
        Pair<String, String> pair = utils.Create("1");
        ECKey key = utils.GetKey(pair.getFirst(), pair.getSecond());
        Assertions.assertNotNull(key);
    }

    @Test
    void keyShouldNotBeCreatedWhenPasswordIsIncorrect() {
        when(properties.getKeyPassword()).thenReturn("123456");
        Assertions.assertThrows(IllegalStateException.class, () -> {
            KeyUtils utils = new KeyUtils(properties);
            Pair<String, String> pair = utils.Create("1");
            when(properties.getKeyPassword()).thenReturn("1234567");
            ECKey key = utils.GetKey(pair.getFirst(), pair.getSecond());
        });
    }

    @Test
    void keyShouldNotBeCreatedWhenSaltIsIncorrect() {
        when(properties.getKeyPassword()).thenReturn("123456");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            KeyUtils utils = new KeyUtils(properties);
            Pair<String, String> pair = utils.Create("1");
            ECKey key = utils.GetKey(pair.getFirst() + ".", pair.getSecond());
        });
    }
}
