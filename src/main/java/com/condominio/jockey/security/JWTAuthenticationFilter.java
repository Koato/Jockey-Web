package com.condominio.jockey.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.condominio.jockey.beans.Usuario;
import com.condominio.jockey.security.services.PersonalizacionUsuarioDetalle;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		try {
			Usuario credenciales = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					credenciales.getAlias(), credenciales.getClave(), Collections.emptyList());
			return authenticationManager.authenticate(authenticationToken);
		} catch (IOException | RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
//		obtengo la clase del logueo exitoso
		PersonalizacionUsuarioDetalle usuarioDetalle = (PersonalizacionUsuarioDetalle) auth.getPrincipal();
//		traslado la informacion al usuario
		Usuario usuario = usuarioDetalle.getUsuario();
		String token = Jwts.builder().setId("softtekJWT").setSubject(usuario.getAlias())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + Constantes.TOKEN_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, Constantes.SECRET_KEY.getBytes()).compact();
		response.addHeader(Constantes.HEADER, Constantes.PREFIX + " " + token);
	}
}