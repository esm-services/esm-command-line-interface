package com.esm.cli;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SpringBootApplication
@EnableOAuth2Client
public class ESMCLIApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ESMCLIApplication.class, args);
	}
	
	@Bean
	public OAuth2RestTemplate restTemplate() {
		return new OAuth2RestTemplate(resource(), new DefaultOAuth2ClientContext());
	}

	@Bean
	protected OAuth2ProtectedResourceDetails resource() {
		ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();
		details.setScope(Arrays.asList("read", "write"));
		details.setClientAuthenticationScheme(AuthenticationScheme.header);
		details.setAccessTokenUri("http://localhost:9999/oauth2/services/oauth/token");
		details.setClientId("commandlineinterface");
		details.setClientSecret("commandlineinterface");
		details.setGrantType("password");
		details.setUsername("user");
		details.setPassword("password");
		return details;
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("Starting cron job ::==>");

		OAuth2RestTemplate template = restTemplate();

		String token = template.getAccessToken().getValue();
		System.out.println("Token = " + token);

		String data = template.getForObject("http://localhost:9090/services/message", String.class);
		System.out.println("Account Data ==> " + data);

		System.out.println("==============================================================================");
	}
}
