package com.condominio.jockey.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.condominio.jockey.security.services.implement.JwtEntryPoint;

@Configuration
@EnableWebSecurity
class Security extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtEntryPoint entryPoint;

	@ConditionalOnMissingBean
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Se desactiva el uso de cookies
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				// Cords por defecto
				.and().cors()
				// Se desactiva el filtro CSRF
				.and().csrf().disable()
				// Configuro el mensaje para el punto de acceso
				.exceptionHandling().authenticationEntryPoint(entryPoint).and()
				// Se indica que el resto de URLs esten securizadas
				.addFilter(new JWTAuthorizationFilter(authenticationManager()))
				.addFilter(new JWTAuthenticationFilter(authenticationManager())).authorizeRequests().anyRequest()
				.authenticated();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// Se indica que swagger no requiere autenticación
		// Se indica que graphiql, pero el servicio se graphql si lo requiere
		// no se agrega el login
		web.ignoring().antMatchers("/swagger-ui.html", "/v2/api-docs", "/swagger-resources/**", "/webjars/**",
				"/graphiql", "/vendor/**");
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Se define la clase que recupera los usuarios y el algoritmo para procesar las
		// passwords
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
	}
}
