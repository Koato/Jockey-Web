package com.condominio.jockey.beans;

import java.util.List;

import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Document(collection = "usuario")
@ApiModel("Modelo de Usuario")
public class Usuario {
	@Id
	@ApiModelProperty(value = "Identificador de usuario", required = true)
	private String id;

	@NotEmpty(message = "no puede estar vacio")
	@ApiModelProperty(value = "Alias de usuario", required = true)
	private String alias;

	@NotEmpty(message = "no puede estar vacio")
	@ApiModelProperty(value = "Clave de usuario", required = true)
	private String clave;

	@NotEmpty(message = "no puede estar vacio")
	@ApiModelProperty(value = "Fecha y hora de acceso del usuario", required = true)
	private String tiempoAcceso;

	@ApiModelProperty(value = "Estado de usuario", required = true)
	private boolean estado;

	@NotEmpty(message = "no puede estar vacio")
	@ApiModelProperty(value = "Lista de roles de usuario", required = true)
	private List<String> roles;
	
	@Transient
	private String captchaResponse;
}
