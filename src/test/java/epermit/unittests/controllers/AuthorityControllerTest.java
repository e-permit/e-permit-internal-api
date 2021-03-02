package epermit.unittests.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import an.awesome.pipelinr.Pipeline;
import epermit.commands.CreateAuthorityCommand;
import epermit.common.CommandResult;
import epermit.common.MvcTestUtils;
import epermit.controllers.AuthorityController;
import epermit.core.aurthorities.AuthorityDto;
import epermit.data.commandhandlers.CreateAuthorityCommandHandler;

@WebMvcTest(AuthorityController.class)
@ExtendWith(MockitoExtension.class)

public class AuthorityControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CreateAuthorityCommandHandler handler;

	@MockBean
	private Pipeline pipeline;

	@Test
	@WithMockUser(value = "admin")
	public void getTest() throws Exception {
		when(handler.handle(new CreateAuthorityCommand())).thenReturn(CommandResult.success());
		when(pipeline.send(any(CreateAuthorityCommand.class))).thenReturn(CommandResult.success());
		MvcResult mvcResult = mockMvc.perform(get("/authorities")).andDo(print())
				.andExpect(status().isOk()).andReturn();
		CommandResult r = MvcTestUtils.parseResponse(mvcResult, CommandResult.class);
		assertEquals(r.getSucceed(), true);
	}
}
