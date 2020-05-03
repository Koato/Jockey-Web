package com.condominio.jockey.input;

import java.util.List;

import lombok.Data;

@Data
public class UsuarioInput {
	private String id;
	private String alias;
	private String clave;
	private boolean estado;
	private List<String> roles;
}