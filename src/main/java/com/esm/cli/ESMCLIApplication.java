package com.esm.cli;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;

@SpringBootApplication
public class ESMCLIApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ESMCLIApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("Starting cron job");

		ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();
		details.setClientAuthenticationScheme(AuthenticationScheme.header);
		details.setAccessTokenUri("http://localhost:8888/services/oauth/token");

		details.setScope(Arrays.asList("employee_read", "employee_write", "employee_trust"));

		details.setClientId("employee-service");
		details.setClientSecret("employee-service-secret");

		details.setUsername("durgesh");
		details.setPassword("durgesh");

		OAuth2RestTemplate template = new OAuth2RestTemplate(details);

		String token = template.getAccessToken().getValue();
		System.out.println("Token = " + token);

		String data = template.getForObject("http://localhost:9090/services/message", String.class);
		System.out.println("Data = " + data);
	}
}
