package com.condominio.jockey.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader(ConstantesSeguridad.HEADER);
		if (!requiresAuthentication(header)) {
			filterChain.doFilter(request, response);
			return;
		}

		boolean validToken = false;
		Claims claims = null;
		try {
//			obtengo el token del header
			String token = request.getHeader(ConstantesSeguridad.HEADER).replace(ConstantesSeguridad.PREFIX, "");
			claims = Jwts.parser().setSigningKey(ConstantesSeguridad.SECRET_KEY.getBytes()).parseClaimsJws(token)
					.getBody();
			validToken = true;
		} catch (JwtException | IllegalArgumentException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
		} catch (NullPointerException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}

		UsernamePasswordAuthenticationToken auth = null;
		if (validToken) {
			String usuario = claims.getSubject();
			Object roles = claims.get(ConstantesSeguridad.AUTHORITIES);
			Collection<? extends GrantedAuthority> authorities = Arrays
					.asList(new ObjectMapper().addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
							.readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
			auth = new UsernamePasswordAuthenticationToken(usuario, null, authorities);
		}
		SecurityContextHolder.getContext().setAuthentication(auth);
		filterChain.doFilter(request, response);
	}

	private boolean requiresAuthentication(String header) {
		if (header == null || !header.startsWith(ConstantesSeguridad.PREFIX)) {
			return false;
		}
		return true;
	}
}