package com.condominio.jockey.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.condominio.jockey.beans.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {

	Usuario findFirstByAlias(String alias);

	List<Usuario> findAll();

	@Query("{alias: '?0'}")
	List<Usuario> findCustomByAlias(String alias);
}
