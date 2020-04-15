package com.condominio.jockey.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.condominio.jockey.beans.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
	
	Usuario findFirstByAliasAndClave(String alias, String clave);
	
	List<Usuario> findAll();
	
	@Query(collation = "{alias: '?0'}")
	List<Usuario> findCustomByAlias(String alias);
}
