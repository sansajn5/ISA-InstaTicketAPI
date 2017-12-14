package isa.instaTicketAPI.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import isa.instaTicketAPI.DatabasesTest;
import isa.instaTicketAPI.web.rest.controllers.EntryContoller;
import isa.instaTicketAPI.web.rest.dto.RegistrateUserDTO;

public class EntryTest extends DatabasesTest {

	private MockMvc mockMvc;
	
	@Autowired
	private EntryContoller entryContoller;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(entryContoller).build();
	}
	
	@Test
	@Transactional
	public void registration() throws Exception {
		RegistrateUserDTO register = new RegistrateUserDTO();
		register.setName("Nemanja");
		register.setLastName("Mudrinic");
		register.setEmail("mudrinic5n@outlook.com");
		register.setAuthority("admin");
		register.setPassword("isa");
		register.setUsername("sansajn");

		String jsonContent = "";

		try {
			ObjectMapper mapper = new ObjectMapper();
			jsonContent = mapper.writeValueAsString(register);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		this.mockMvc
				.perform(post("/api/entry/signup")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(jsonContent)).andExpect(status().isOk());
	}

}
