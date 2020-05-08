package com.condominio.jockey.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.condominio.jockey.beans.Usuario;
import com.condominio.jockey.services.implement.UsuarioServicesImpl;

import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.relay.Connection;
import graphql.relay.SimpleListConnection;
import graphql.schema.DataFetchingEnvironment;

@Component
public class Query implements GraphQLQueryResolver {

	@Autowired
	private UsuarioServicesImpl usuarioServicesImpl;

//	listado paginado
	public Connection<Usuario> obtenerUsuarios(int first, String after, DataFetchingEnvironment env) {
		Page<Usuario> usuarios = usuarioServicesImpl.findAll(null);
		return new SimpleListConnection<>(usuarios.getContent()).get(env);
	}

	public Usuario obtenerUsuario(String id) {
		try {
			return usuarioServicesImpl.findById(id);
		} catch (Exception e) {
			return null;
		}
	}
}