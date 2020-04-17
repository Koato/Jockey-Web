package com.condominio.jockey.services;

import java.util.List;

import com.condominio.jockey.beans.Usuario;

public interface UsuarioServices {
	List<Usuario> findAll();

	Usuario findById(String id);

	Usuario guardarUsuario(Usuario usuario);

	void actualizarUsuario(Usuario usuario);

	void eliminarUsuario(String id);
}