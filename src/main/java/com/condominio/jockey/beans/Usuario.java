package com.condominio.jockey.beans;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "usuario")
public class Usuario {
	@Indexed
	private String id;
	private String alias;
	private String clave;
	private String tiempoAcceso;
	private boolean estado;
}
