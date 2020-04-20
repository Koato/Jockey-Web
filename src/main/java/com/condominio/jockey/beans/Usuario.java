package com.condominio.jockey.beans;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "usuario")
public class Usuario {
	@Indexed
	private String id;
	@NotEmpty(message = "No puede estar vacio")
	private String alias;
	@NotEmpty(message = "No puede estar vacio")
	private String clave;
	@NotEmpty(message = "No puede estar vacio")
	private String tiempoAcceso;
	private boolean estado;
	@NotEmpty(message = "No puede estar vacio")
	private List<String> roles;
}
