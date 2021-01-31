package epermit.unittests.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import epermit.data.entities.Authority;
import epermit.data.repositories.AuthorityRepository;
import epermit.data.services.AuthorityServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AuthorityServiceTest {

	@Mock
	AuthorityRepository repository;

	@Test
	public void xTest() {
		List<Authority> authorities = new ArrayList<>();
		Authority a = new Authority();
		a.setId((long)12);
		authorities.add(a);
		when(repository.findAll()).thenReturn(authorities);
		AuthorityServiceImpl service = new AuthorityServiceImpl(repository, new ModelMapper());
		assertEquals(service.getAll().size(), 1);
		Mockito.verify(repository, Mockito.times(1)).findAll();
	}
}
