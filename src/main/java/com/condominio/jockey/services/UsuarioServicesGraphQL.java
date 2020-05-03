package com.condominio.jockey.services;

import java.util.List;

import com.condominio.jockey.beans.Usuario;

public interface UsuarioServicesGraphQL {
	List<Usuario> findAll();

	Usuario findById(String id);

	Usuario guardarUsuario(Usuario usuario);

	Usuario actualizarUsuario(Usuario usuario);

	Usuario eliminarUsuario(String id);
}