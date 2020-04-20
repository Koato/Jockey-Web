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
	@NotEmpty
	private String alias;
	@NotEmpty
	private String clave;
	@NotEmpty
	private String tiempoAcceso;
	private boolean estado;
	@NotEmpty
	private List<String> roles;
}
