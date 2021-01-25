package epermit.unittests;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import epermit.controllers.AuthorityController;
import epermit.core.aurthorities.Authority;
import epermit.core.aurthorities.AuthorityService;

@WebMvcTest(AuthorityController.class)
public class AuthorityControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthorityService service;

	private static final ObjectMapper MAPPER = new ObjectMapper()
			.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule());

	public static <T> T parseResponse(MvcResult result, Class<T> responseClass) {
		try {
			String contentAsString = result.getResponse().getContentAsString();
			return MAPPER.readValue(contentAsString, responseClass);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void greetingShouldReturnMessageFromService() throws Exception {
		List<Authority> authorities = new ArrayList<>();
		Authority a = new Authority();
		a.setId((long)12);
		authorities.add(a);
		when(service.getAll()).thenReturn(authorities);
		MvcResult mvcResult = this.mockMvc.perform(get("/authorities")).andDo(print()).andExpect(status().isOk())
				.andReturn();
		Authority[] r = parseResponse(mvcResult, Authority[].class);
		assertEquals(r.length, 1);
	}
}
