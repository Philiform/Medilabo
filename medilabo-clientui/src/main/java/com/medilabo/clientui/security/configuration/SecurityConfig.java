package com.medilabo.clientui.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.medilabo.clientui.security.LoginSuccessHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final LoginSuccessHandler loginSuccessHandler;
	
	@Bean
	InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		return new InMemoryUserDetailsManager(
			User.withUsername("doctor1").password(passwordEncoder().encode("123456"))
				.roles("DOCTOR").build(),
			User.withUsername("doctor2").password(passwordEncoder().encode("123456"))
				.roles("DOCTOR").build()
			);
	}

	@Bean
 	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
 		http.formLogin((form) -> form
				.successHandler(loginSuccessHandler)
				);

		http.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/patient/**").hasRole("DOCTOR")
				.requestMatchers("/css/**").permitAll()
				.anyRequest()
				.authenticated());
		
		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}