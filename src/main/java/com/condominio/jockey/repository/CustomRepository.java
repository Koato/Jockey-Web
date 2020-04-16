package com.condominio.jockey.repository;

public interface CustomRepository {

//	modificar la fecha y hora de acceso
	long updateUsuario(String alias, String tiempoAcceso);
	
	void eliminarUsuario(String id); 
}
