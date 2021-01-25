package epermit.unittests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import epermit.data.entities.Authority;
import epermit.data.repositories.AuthorityRepository;
import epermit.data.services.AuthorityServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AuthorityServiceTest {

	@Mock
	AuthorityRepository repository;

	/*@InjectMocks
	AuthorityServiceImpl service;*/

	@Test
	public void greetingShouldReturnMessageFromService() {
		List<Authority> authorities = new ArrayList<>();
		Authority a = new Authority();
		a.setId((long)12);
		authorities.add(a);
		when(repository.findAll()).thenReturn(authorities);
		AuthorityServiceImpl service = new AuthorityServiceImpl(repository, new ModelMapper());
		assertEquals(service.getAll().size(), 1);
	}
}
