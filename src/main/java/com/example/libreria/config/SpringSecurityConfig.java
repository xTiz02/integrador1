package com.example.libreria.config;



import com.example.libreria.handlers.LoginSuccessHandler;
import com.example.libreria.services.impl.JpaUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;


import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginSuccessHandler successHandler;

	@Autowired
	private JpaUserDetailService jpaUserDetailService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()//desactiva csrf que es un token de seguridad para evitar ataques
				.authorizeRequests().antMatchers( "/resources/**", "/static/**", "/custom/**", "/vendors/**", "/images/**", "/locale/**").permitAll()
				.anyRequest().authenticated()//cualquier otra ruta requiere autenticacion
				.and()
				.formLogin()
				.successHandler(successHandler)
				.loginPage("/login")
				.permitAll()//todos pueden acceder a login
				.and()
				.logout().permitAll()//todos pueden acceder a logout
				.and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login").and().exceptionHandling()//si el logout es correcto, redirige a login
				.accessDeniedPage("/access-denied");//si el usuario no tiene permisos, redirige a access-denied


	}


	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception
	{
		build.userDetailsService(jpaUserDetailService)
				.passwordEncoder(passwordEncoder);
	}


}
