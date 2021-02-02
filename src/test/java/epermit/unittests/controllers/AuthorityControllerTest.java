package epermit.unittests.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import epermit.common.MvcTestUtils;
import epermit.controllers.AuthorityController;
import epermit.core.aurthorities.AuthorityDto;
import epermit.core.aurthorities.AuthorityService;

@WebMvcTest(AuthorityController.class)
@ExtendWith(MockitoExtension.class)
public class AuthorityControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthorityService service;

	@Test
	public void xTest() throws Exception {
		List<AuthorityDto> authorities = new ArrayList<>();
		AuthorityDto a = new AuthorityDto();
		a.setId((long) 12);
		authorities.add(a);
		when(service.getAll()).thenReturn(authorities);
		MvcResult mvcResult = mockMvc.perform(get("/authorities")).andDo(print()).andExpect(status().isOk())
				.andReturn();
		AuthorityDto[] r = MvcTestUtils.parseResponse(mvcResult, AuthorityDto[].class);
		assertEquals(r.length, 1);
	}
}
