package com.example;

import static org.junit.Assert.assertNotNull;

import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This Spring MVC Test Framework test can run anytime, whether
 * the server is running or not.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class RestBasicAuthTestMVCTest {

	RestTemplate template = new RestTemplate();
	String user = "user";
	String password = "password";
    
	@Autowired WebApplicationContext wac;
	@Autowired FilterChainProxy springSecurityFilterChain;
    MockMvc mockMvc;

    @Before
    public void setup() {
          // Initialize mockMvc using WebApplicationContext
          mockMvc = MockMvcBuilders
        		  .webAppContextSetup(wac)
        		  .addFilter(springSecurityFilterChain, "/*")
        		  .build();
    }	
    
    
	@Test
	public void test() throws Exception {
		
		//	Take user and password, concatenate with :, and URL encode:
		String base64Creds = this.encodeBase64(user + ":" + password);
		
		//	Make this the Authorization header and build a request:
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		mockMvc.perform(
			get("/teams/1")
			.header("Authorization", "Basic " + base64Creds)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value("Lions"))
			.andExpect(jsonPath("$.location").value("Detroit"))
			;
	}

	
	/**
	 * Encode the given String in Base 64:
	 */
	private String encodeBase64(String input) {
		byte[] inputBytes = input.getBytes();
		byte[] base64Bytes = Base64.encodeBase64(inputBytes);
		return new String(base64Bytes);
	}
	
	
	
}
