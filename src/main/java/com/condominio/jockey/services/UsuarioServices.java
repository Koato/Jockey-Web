package com.condominio.jockey.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.condominio.jockey.beans.Usuario;

public interface UsuarioServices {
	Page<Usuario> findAll(Pageable pageable);

	Usuario findById(String id);

	Usuario guardarUsuario(Usuario usuario);

	Usuario actualizarUsuario(Usuario usuario);

	Usuario eliminarUsuario(String id);
}