package com.example;

import static org.junit.Assert.assertNotNull;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestBasicAuthTest {

	String user = "user";
	String password = "password";
	
	@Test
	public void test() {
		
		//	Take user and password, concatenate with :, and URL encode:
		String plainCreds = user + ":" + password;
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		
		//	Make this the Authorization header and build a request:
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		//	Use a template to send the request and get the response:
		RestTemplate template = new RestTemplate();
		ResponseEntity<Team> teamResponse = 
			template.exchange(
				"http://localhost:8080/teams/1", 
				HttpMethod.GET, 
				request, Team.class);
		Team team = teamResponse.getBody();		
		
		assertNotNull(team);
		
	}

}
