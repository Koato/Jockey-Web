package com.condominio.jockey.repository;

public interface CustomRepository {

//	modificar la hora de acceso
	long updateUsuario(String alias, String tiempoAcceso);
}
