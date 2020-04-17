package com.condominio.jockey.repository;

import com.condominio.jockey.beans.Usuario;

public interface CustomRepository {

//	modificar la fecha y hora de acceso
	long updateUsuario(Usuario usuario);
	
	void eliminarUsuario(String id); 
}
