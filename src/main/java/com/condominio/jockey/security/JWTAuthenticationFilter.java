package com.condominio.jockey.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.condominio.jockey.beans.Usuario;
import com.condominio.jockey.security.services.PersonalizacionUsuarioDetalle;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

//	intento de ingreso
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		try {
//			informacion enviada en el body de la solicitud como raw
			Usuario credenciales = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					credenciales.getAlias(), credenciales.getClave(), Collections.emptyList());
			return authenticationManager.authenticate(authenticationToken);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

//	ingreso satisfactorio
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
//		obtengo la clase del logueo exitoso
		PersonalizacionUsuarioDetalle usuarioDetalle = (PersonalizacionUsuarioDetalle) auth.getPrincipal();
//		traslado la informacion al usuario
		Usuario usuario = usuarioDetalle.getUsuario();
//		asignacion de roles obtenidos
		List<GrantedAuthority> userAuthorities = new ArrayList<>();
		for (String rol : usuario.getRoles()) {
			userAuthorities.add(new SimpleGrantedAuthority(rol));
		}
		Map<String, Object> body = new HashMap<>();
		User user = new User(usuario.getAlias(), usuario.getClave(), usuario.isEstado(), true, true, true,
				userAuthorities);
//		obtengo los roles del usuario
		Collection<? extends GrantedAuthority> roles = user.getAuthorities();
		Claims claims = Jwts.claims();
		claims.put(Constantes.AUTHORITIES, new ObjectMapper().writeValueAsString(roles));
		String token = Jwts.builder().setSubject(usuario.getAlias())
//				indico los roles que tiene
				.setClaims(claims)
//				fecha de creacion
				.setIssuedAt(new Date())
//				fecha de expiracion
				.setExpiration(new Date(System.currentTimeMillis() + Constantes.TOKEN_EXPIRATION_TIME))
//				algoritmo de cifrado
				.signWith(SignatureAlgorithm.HS512, Constantes.SECRET_KEY.getBytes()).compact();
		response.addHeader(Constantes.HEADER, Constantes.PREFIX + " " + token);
//		no quiero que se muestre la clave del usuario
		user.eraseCredentials();
		body.put("usuario", user);
		body.put("token", token);
		body.put("mensaje", "Se ha logueado correctamente");
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
//		la respuesta debe mostrarse como json
		response.setContentType("application/json");
//		indico que fue exitoso
		response.setStatus(200);
	}

//	ingreso fallido
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		Map<String, Object> body = new HashMap<>();
		body.put("mensaje", "Credenciales ingresadas son erróneas");
		body.put("error", failed.getMessage());
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
//		la respuesta debe mostrarse como json
		response.setContentType("application/json");
//		indico que no esta autorizado
		response.setStatus(401);
	}
}