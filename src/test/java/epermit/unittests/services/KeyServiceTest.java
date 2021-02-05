package epermit.unittests.services;

import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.support.TransactionTemplate;
import epermit.common.CommandResult;
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

    @Test
    public void shouldGetEnabledKey() {
        when(properties.getKeyPassword()).thenReturn("123456");
        KeyUtils utils = new KeyUtils(properties, null);
        Key key = utils.Create("1");
        when(repository.save(key)).thenReturn(key);
        KeyServiceImpl service = new KeyServiceImpl(repository, mapper, utils, template);
        Assertions.assertNotNull(service.EnableKey(key.getId()));
    }

    @Test
    public void shouldCreateKey() {
        when(properties.getKeyPassword()).thenReturn("123456");
        KeyUtils utils = new KeyUtils(properties, null);
        Key key =utils.Create("1"); 
        when(repository.findOneByEnabledTrue().get()).thenReturn(key);
        KeyServiceImpl service = new KeyServiceImpl(repository, mapper, utils, template);
        CommandResult r = service.CreateKey("1");
        Assertions.assertNotNull(r.getProps().get("key_id"));
    }

    /*
     * @Test public void shouldEnableKey() { repository.findById(id);
     * when(repository.getEnabled()).thenReturn(key); KeyServiceImpl service = new
     * KeyServiceImpl(repository, "123456"); Assertions.assertNotNull(service.getCurrentKey()); }
     */
        /*
     * @BeforeEach void setup(){ ReflectionTestUtils.setField(KeyServiceImpl.class,
     * "epermit.key.password", "123456"); }
     */
}
