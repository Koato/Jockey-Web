package com.condominio.jockey.resolver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
		List<Usuario> usuarios = usuarioServicesImpl.findAll();
		return new SimpleListConnection<>(usuarios).get(env);
	}

	public Usuario obtenerUsuario(String id) {
		try {
			return usuarioServicesImpl.findById(id);
		} catch (Exception e) {
			return null;
		}
	}
}