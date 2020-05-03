package com.condominio.jockey.mutation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.condominio.jockey.beans.Usuario;
import com.condominio.jockey.input.UsuarioInput;
import com.condominio.jockey.services.implement.UsuarioServicesImplGraphQL;

import graphql.kickstart.tools.GraphQLMutationResolver;

@Component
public class Mutation implements GraphQLMutationResolver {

	@Autowired
	private UsuarioServicesImplGraphQL usuarioServicesImpl;

	public Usuario crearUsuario(UsuarioInput usuario) {
		Usuario us = new Usuario();
		us.setAlias(usuario.getAlias());
		us.setClave(usuario.getClave());
		us.setEstado(usuario.isEstado());
		us.setRoles(usuario.getRoles());
		us.setTiempoAcceso("27/04/2020");
		return usuarioServicesImpl.guardarUsuario(us);
	}

	public Usuario actualizarUsuario(String id, UsuarioInput usuario) {
		Usuario us = new Usuario();
		us.setId(id);
		us.setAlias(usuario.getAlias());
		us.setClave(usuario.getClave());
		us.setEstado(usuario.isEstado());
		us.setRoles(usuario.getRoles());
		us.setTiempoAcceso("27/04/2020");
		return usuarioServicesImpl.actualizarUsuario(us);
	}

	public Usuario eliminarUsuario(String id) {
		return usuarioServicesImpl.eliminarUsuario(id);
	}
}