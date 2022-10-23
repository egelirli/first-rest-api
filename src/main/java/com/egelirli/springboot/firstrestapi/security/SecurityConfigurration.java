package com.egelirli.springboot.firstrestapi.security;

import static org.springframework.security.config.Customizer.withDefaults;
import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfigurration {

	//LDAP Config
	//In memory
	
	@Bean
	public InMemoryUserDetailsManager createUserDetailsManager() {
		
		Function<String, String> passwdEncoder = 
					input ->  passwordEncoder().encode(input);
		
		
		UserDetails userDetails1 = createNewUserDetail(passwdEncoder, "veli", "dummy");
		UserDetails userDetails2 = createNewUserDetail(passwdEncoder, "deli", "mummy");
		
		return new InMemoryUserDetailsManager(userDetails1, userDetails2);	 
	}


	private UserDetails createNewUserDetail(
			             Function<String, String> passwdEncoder, 
			             String username, 
			             String password) {
		
		UserDetails userDetails = User.builder()
				 .passwordEncoder(passwdEncoder)
				 .username(username)
				 .password(password)
				 .roles("ADMIN", "DEVELOPER")
				 .build();
		return userDetails;
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	//All URLs are protected
	//A login form is shown for unauthorized requests
	//CSRF disable
	//FRames
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				t ->  t.anyRequest().authenticated());
		http.httpBasic(withDefaults());
		http.csrf().disable(); //For POST and PUT
		http.headers().frameOptions().disable();
		
		return http.build();
		
	}
	
	
	
	
}
