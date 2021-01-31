package epermit.unittests.utils;


import com.nimbusds.jose.jwk.ECKey;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

import epermit.utils.KeyUtils;

public class KeyUtilTest {
    @Test
    void keyShouldBeCreatedWhenSaltAndPasswordIsCorrect() {
        KeyUtils utils = new KeyUtils("123456");
        Pair<String, String> pair = utils.Create("1");
        ECKey key = utils.GetKey(pair.getFirst(), pair.getSecond());
        Assertions.assertNotNull(key);
    }

    @Test
    void keyShouldNotBeCreatedWhenPasswordIsIncorrect() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            KeyUtils utils = new KeyUtils("123456");
            Pair<String, String> pair = utils.Create("1");
            ECKey key = utils.GetKey(pair.getFirst(), pair.getSecond());
        });
    }

    @Test
    void keyShouldNotBeCreatedWhenSaltIsIncorrect() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            KeyUtils utils = new KeyUtils("123456");
            Pair<String, String> pair = utils.Create("1");
            ECKey key = utils.GetKey(pair.getFirst() + ".", pair.getSecond());
        });
    }
}
