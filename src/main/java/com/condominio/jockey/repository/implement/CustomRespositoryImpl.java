package com.condominio.jockey.repository.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.condominio.jockey.beans.Usuario;
//import com.condominio.jockey.beans.Usuario;
import com.condominio.jockey.repository.CustomRepository;
import com.mongodb.client.result.UpdateResult;

@Component
public class CustomRespositoryImpl implements CustomRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public long updateUsuario(String alias, String tiempoAcceso) {
		Query query = new Query(Criteria.where("alias").is(alias));
		Update update = new Update();
		update.set("tiempoAcceso", tiempoAcceso);
//		UpdateResult result = mongoTemplate.updateFirst(query, update, Usuario.class);
		UpdateResult result = mongoTemplate.upsert(query, update, Usuario.class);
		if (result != null) {
			return result.getModifiedCount();
		} else {
			return 0;
		}
	}

	@Override
	public void eliminarUsuario(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		mongoTemplate.findAndRemove(query, Usuario.class);
	}

}
