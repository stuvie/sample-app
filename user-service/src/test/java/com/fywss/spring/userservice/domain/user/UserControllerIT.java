package com.fywss.spring.userservice.domain.user;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fywss.spring.userservice.UserServiceApplication;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { UserServiceApplication.class })
@WebAppConfiguration
@SpringBootTest
public class UserControllerIT {
	
	private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testFindAllResponse() throws Exception {
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users"))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(Matchers.greaterThanOrEqualTo(2))))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("Steve"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value("Ziggy"))
				.andReturn();

		Assert.assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType());
	}

	@Test
	public void testFindByIdResponse() throws Exception {
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/2"))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Ziggy"))
				.andReturn();

		Assert.assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType());
	}
	
	@Test
	public void testFindByIdNotFoundError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/20"))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()));
	}
	
	@Test
	public void testFindByEmailResponse() throws Exception {
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users?email=sk@fywss.com"))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Steve"))
				.andReturn();

		Assert.assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType());
	}
	
	@Test
	public void testFindByEmailNotFoundError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users?email=rob@google.com"))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()));
	}
	
	@Test
	public void testCreateValidUserResponse() throws Exception {
		User user = new User("bob", "manson", "bob@gmail.com", "Good4password");
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(CONTENT_TYPE)
				.content(asJsonString(user)))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(CONTENT_TYPE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("bob@gmail.com"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("manson"));
	}
	
	@Test
	public void testCreateDuplicateUserResponse() throws Exception {
		User user = new User("steve", "kots", "sk@fywss.com", "Good4password");
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(CONTENT_TYPE)
				.content(asJsonString(user)))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is(HttpStatus.CONFLICT.value()));
	}
	
	@Test
	public void testCreateUserBadPasswordAlgorithmResponse() throws Exception {
		User user = new User("steve", "kots", "throw@fywss.com", "Abcdefgh44");
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(CONTENT_TYPE)
				.content(asJsonString(user)))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
	}
	
	@Test
	public void testUpdateUserResponse() throws Exception {
		User user = new User("Ziggy", "Marley", "marley@gmail.com", "Good4password");
		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/2").contentType(CONTENT_TYPE)
				.content(asJsonString(user)))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(CONTENT_TYPE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("marley@gmail.com"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Marley"));
	}
	
	@Test
	public void testUpdateUserNotFoundError() throws Exception {
		User user = new User("Ziggy", "Marley", "marley@gmail.com", "Good4password");
		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/20").contentType(CONTENT_TYPE)
				.content(asJsonString(user)))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()));
	}
	
	@Test
	public void testUpdateUserEncryptionExceptionResponse() throws Exception {
		User user = new User("Ziggy", "Marley", "marley@gmail.com", "Abcdefgh44");
		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/2").contentType(CONTENT_TYPE)
				.content(asJsonString(user)))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(Matchers.startsWith("EncryptionException")));
	}
	
	@Test
	public void testDeleteUserResponse() throws Exception {
		User user = new User("bob", "manson", "bobby@gmail.com", "Good4password");
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(CONTENT_TYPE)
				.content(asJsonString(user)))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(CONTENT_TYPE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("bobby@gmail.com"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(Matchers.greaterThanOrEqualTo(3)));
		
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/3").contentType(CONTENT_TYPE)
				.content(asJsonString(user)))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(CONTENT_TYPE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("bob"));
	}
	
	@Test
	public void testDeleteUserNotFoundError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/20"))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()));
	}
	
	private String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        // System.out.println(jsonContent);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
