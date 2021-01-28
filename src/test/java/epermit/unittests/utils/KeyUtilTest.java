package epermit.unittests.utils;


import com.nimbusds.jose.jwk.ECKey;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

import epermit.utils.KeyUtils;

public class KeyUtilTest {
    @Test
    void keyShouldBeCreatedWhenSaltAndPasswordIsCorrect() {
        Pair<String, String> pair = KeyUtils.Create("1", "123456");
        ECKey key = KeyUtils.GetKey("123456", pair.getFirst(), pair.getSecond());
        Assertions.assertNotNull(key);
    }

    @Test
    void keyShouldNotBeCreatedWhenPasswordIsIncorrect() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Pair<String, String> pair = KeyUtils.Create("1", "123456");
            ECKey key = KeyUtils.GetKey("1234567", pair.getFirst(), pair.getSecond());
        });
    }

    @Test
    void keyShouldNotBeCreatedWhenSaltIsIncorrect() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Pair<String, String> pair = KeyUtils.Create("1", "123456");
            ECKey key = KeyUtils.GetKey("123456", pair.getFirst() + ".", pair.getSecond());
        });
    }
}
