package com.areguig.sample.config.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * Created by akli on 14/08/15.
 * <p/>
 * <p/>
 * This class is the configuration class to the Oauth2 resource server
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration
		extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// All the endpoints that match the pattern need an authenticated user
		// with the scope 'sample-scope'.
		http.requestMatchers().antMatchers("/protected/**").and()
				.authorizeRequests().anyRequest()
				.access("#oauth2.hasScope('sample-scope')");
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources)
			throws Exception {
		resources.tokenStore(tokenStore()).resourceId("sample-resource");
	}

	@Bean
	public InMemoryTokenStore tokenStore() {
		return new InMemoryTokenStore();
	}

}
