package com.condominio.jockey.beans;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "usuario")
public class Usuario {
	@Id
	private String id;
	private String alias;
	private String clave;
	private String tiempoAcceso;
	private boolean estado;
}
