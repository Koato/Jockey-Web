package com.condominio.jockey.exception;

import org.springframework.core.NestedRuntimeException;

public class UserNotFoundException extends NestedRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String id) {
		super("El usuario con ID '" + id + "' no ha sido encontrado");
	}
}
