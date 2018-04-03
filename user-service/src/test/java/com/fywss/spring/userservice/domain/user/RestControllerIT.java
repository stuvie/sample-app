package com.fywss.spring.userservice.domain.user;

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
public class RestControllerIT {
	
	private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testGetUserResponse() throws Exception {
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/2"))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Ziggy"))
				.andReturn();

		Assert.assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType());
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
