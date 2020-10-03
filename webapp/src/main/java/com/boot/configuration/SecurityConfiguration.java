package com.boot.configuration;

import org.jasypt.springsecurity4.crypto.password.PasswordEncoder;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/product/edit/*","/product/new","/product/delete/*","/product/show/*").authenticated()	
			.anyRequest().permitAll()
			.and().formLogin().loginPage("/login").permitAll()
			.and().logout().permitAll();
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}
	@Bean
	public PasswordEncoder passwordEncoder(StrongPasswordEncryptor passwordEncryptor){
	    PasswordEncoder passwordEncoder = new PasswordEncoder();
	    passwordEncoder.setPasswordEncryptor(passwordEncryptor);
	    return passwordEncoder;
	}
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService){
	    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
	    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
	    daoAuthenticationProvider.setUserDetailsService(userDetailsService);
	    return daoAuthenticationProvider;
	}
	@Autowired
	public void configureAuthManager(AuthenticationManagerBuilder authenticationManagerBuilder, @Qualifier("daoAuthenticationProvider") AuthenticationProvider authenticationProvider){
	    authenticationManagerBuilder.authenticationProvider(authenticationProvider);
	}
}
