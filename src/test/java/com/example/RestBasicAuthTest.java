package com.example;

import static org.junit.Assert.assertNotNull;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * WARNING: THIS TEST REQUIRES THE SERVER TO BE RUNNING ON 
 * LOCALHOST:8080
 */
public class RestBasicAuthTest {

	RestTemplate template = new RestTemplate();
	String user = "user";
	String password = "password";
	
	@Test
	public void test() {
		
		//	Take user and password, concatenate with :, and URL encode:
		String base64Creds = this.encodeBase64(user + ":" + password);
		
		//	Make this the Authorization header and build a request:
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		//	Use a template to send the request and get the response:
		ResponseEntity<Team> teamResponse = 
			template.exchange(
				"http://localhost:8080/teams/1", 
				HttpMethod.GET, 
				request, 
				Team.class);
		Team team = teamResponse.getBody();		
		
		assertNotNull(team);
		
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
