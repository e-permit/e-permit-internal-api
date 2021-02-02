package epermit.unittests.services;

import static org.mockito.Mockito.when;

import java.util.Properties;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.Pair;
import org.springframework.transaction.support.TransactionTemplate;
import epermit.config.EPermitProperties;
import epermit.data.entities.Key;
import epermit.data.repositories.KeyRepository;
import epermit.data.services.KeyServiceImpl;
import epermit.data.utils.KeyUtils;

@ExtendWith(MockitoExtension.class)
public class KeyServiceTest {
    @Mock
    KeyRepository repository;

    @Mock
    TransactionTemplate template;
    
    @Mock
    EPermitProperties properties;

    ModelMapper mapper = new ModelMapper();

    /*
     * @BeforeEach void setup(){ ReflectionTestUtils.setField(KeyServiceImpl.class,
     * "epermit.key.password", "123456"); }
     */

    @Test
    public void shouldGetEnabledKey() {
        when(properties.getKeyPassword()).thenReturn("123456");
        KeyUtils utils = new KeyUtils(properties);
        Pair<String, String> p = utils.Create("1");
        Key key = new Key();
        key.setId((long)1);
        key.setContent(p.getSecond());
        key.setEnabled(true);
        key.setKid("1");
        key.setSalt(p.getFirst());
        when(repository.save(key)).thenReturn(key);
        KeyServiceImpl service = new KeyServiceImpl(repository, mapper, utils, template);
        Assertions.assertNotNull(service.EnableKey(key.getId()));
    }

    @Test
    public void shouldCreateKey() {
        when(properties.getKeyPassword()).thenReturn("123456");
        KeyUtils utils = new KeyUtils(properties);
        Pair<String, String> p = utils.Create("1");
        Key key = new Key();
        key.setContent(p.getSecond());
        key.setEnabled(true);
        key.setKid("1");
        key.setSalt(p.getFirst());
        when(repository.getEnabled()).thenReturn(key);
        // when(properties.getKeyPassword()).thenReturn("123456");
        KeyServiceImpl service = new KeyServiceImpl(repository, mapper, utils, template);
        // Assertions.assertNotNull(service.getCurrentKey());
    }

    /*
     * @Test public void shouldEnableKey() { repository.findById(id);
     * when(repository.getEnabled()).thenReturn(key); KeyServiceImpl service = new
     * KeyServiceImpl(repository, "123456"); Assertions.assertNotNull(service.getCurrentKey()); }
     */
}
