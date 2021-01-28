package epermit.unittests.services;

import static org.mockito.Mockito.when;


import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;

import epermit.data.entities.Key;
import epermit.data.repositories.KeyRepository;
import epermit.data.services.KeyServiceImpl;
import epermit.utils.KeyUtils;

@ExtendWith(MockitoExtension.class)
public class KeyServiceTest {
    @Mock
    KeyRepository repository;
    
    /*@BeforeEach
    void setup(){
        ReflectionTestUtils.setField(KeyServiceImpl.class, "epermit.key.password", "123456");
    }*/

	@Test
	public void shouldGetEnabledKey() {
        Pair<String, String> p = KeyUtils.Create("1", "123456");
        Key key = new Key();
        key.setContent(p.getSecond());
        key.setEnabled(true);
        key.setKid("1");
        key.setSalt(p.getFirst());
		when(repository.getEnabled()).thenReturn(key);
		KeyServiceImpl service = new KeyServiceImpl(repository, "123456");
		Assertions.assertNotNull(service.getCurrentKey());
    }
    
    @Test
	public void shouldCreateKey() {
        Pair<String, String> p = KeyUtils.Create("1", "123456");
        Key key = new Key();
        key.setContent(p.getSecond());
        key.setEnabled(true);
        key.setKid("1");
        key.setSalt(p.getFirst());
		when(repository.getEnabled()).thenReturn(key);
		KeyServiceImpl service = new KeyServiceImpl(repository, "123456");
		Assertions.assertNotNull(service.getCurrentKey());
    }
    
    /*@Test
	public void shouldEnableKey() {
        repository.findById(id);
		when(repository.getEnabled()).thenReturn(key);
		KeyServiceImpl service = new KeyServiceImpl(repository, "123456");
		Assertions.assertNotNull(service.getCurrentKey());
	}*/
}
