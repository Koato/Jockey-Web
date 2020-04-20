package com.condominio.jockey.security;

public class Constantes {

	private Constantes() {
		throw new IllegalStateException("Clase de constantes de seguridad");
	}

	// SPIRNG SECURITY
	public static final String LOGIN_URL = "/login";
	public static final String HEADER = "Authorization";
	public static final String PREFIX = "Bearer ";
	public static final String AUTHORITIES = "authorities";
	public static final String ISSUER_INFO = "http://www.jockey.com.pe/";
	public static final String ID = "Jockey";

	// JWT
	public static final String SECRET_KEY = "jO6k3&#7559";
	public static final long TOKEN_EXPIRATION_TIME = 1_000L * 60 * 60 * 4; // 4 horas
}