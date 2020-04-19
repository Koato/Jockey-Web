package com.condominio.jockey.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthoritiesMixin {
	@JsonCreator
	public SimpleGrantedAuthoritiesMixin(@JsonProperty("authority") String role) {
		super();
	}
}