package com.cuponservice.service;

import java.util.List;

import org.aspectj.weaver.tools.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class WebSecurityConfig {

	@Autowired
	UserDetailsService userDetailsService;

	@Bean
	SecurityContextRepository securityContextRepository() {
		return new DelegatingSecurityContextRepository(new RequestAttributeSecurityContextRepository(),
				new HttpSessionSecurityContextRepository());
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}// so this encoder will be use when the request coming to encode the password
		// and compare with what we have in the databse

	@Bean
	AuthenticationManager authManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(provider);
	}

	@Bean // this method receive httpSecurity object
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// http.formLogin();// authentication method
		http.authorizeHttpRequests()// authorize request
				.requestMatchers(HttpMethod.GET, "/couponapi/coupons/{code:^[A-Z]*$}", "/showGetCoupon", "/getCoupon")

//                .hasAnyRole("USER", "ADMIN")
				.permitAll().requestMatchers(HttpMethod.GET, "/showCreateCoupon", "/createCoupon", "createResponse")
				.hasAnyRole("USER", "ADMIN").requestMatchers(HttpMethod.POST, "/couponapi/coupons/", "/saveCoupon")
				.hasRole("ADMIN").requestMatchers(HttpMethod.POST, "/getCoupon").hasAnyRole("USER", "ADMIN")
				.requestMatchers("/", "/login", "showReg", "registerUser").permitAll().anyRequest().denyAll().and()
				.logout().logoutSuccessUrl("/");
//                .and()
//                .csrf()
//                .disable();

		http.cors(corsCustomizer -> {
			CorsConfigurationSource configurationSource = request -> {
				CorsConfiguration corsConfiguration = new CorsConfiguration();
				corsConfiguration.setAllowedOrigins(List.of("localhost:3000", ""));
				corsConfiguration.setAllowedMethods(List.of("GET"));

				return corsConfiguration;
			};
			corsCustomizer.configurationSource(configurationSource);
		});

		http.securityContext(context -> context.requireExplicitSave(true));

		return http.build();

	}
}