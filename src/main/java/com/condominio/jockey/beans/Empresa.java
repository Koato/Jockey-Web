package com.condominio.jockey.beans;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "empresa")
public class Empresa {
	@Id
	private String id;
	private String razonSocial;
	private String ruc;
	private String correo;
	private String celular;
	private String direccion;
}