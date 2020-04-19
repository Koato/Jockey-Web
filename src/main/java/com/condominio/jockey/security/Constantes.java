package com.condominio.jockey.security;

public class Constantes {
	// SPIRNG SECURITY
	public static final String LOGIN_URL = "/login";
	public static final String HEADER = "Authorization";
	public static final String PREFIX = "Bearer ";

	// JWT
	public static final String ISSUER_INFO = "https://www.autentia.com/";
	public static final String SECRET_KEY = "Dell#7559";
	public static final long TOKEN_EXPIRATION_TIME = 864_000_000; // 10 day
//	public static final long TOKEN_EXPIRATION_TIME = 30000; // 30 segundos
	
	public static final String AUTHORITIES = "authorities";
}