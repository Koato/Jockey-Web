package com.condominio.jockey.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.condominio.jockey.beans.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

	Usuario findFirstByAlias(String alias);

	List<Usuario> findAll();

//	indico que campos no quiero traer valor
	@Query(value = "{ alias: ?0 }", fields = "{ tiempoAcceso: 0 }")
	List<Usuario> findByAlias(String alias);
}
