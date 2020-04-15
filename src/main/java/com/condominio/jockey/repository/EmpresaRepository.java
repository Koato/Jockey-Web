package com.condominio.jockey.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.condominio.jockey.beans.Empresa;

public interface EmpresaRepository extends MongoRepository<Empresa, String> {
	Empresa findFirstByRuc(String ruc);
}