package com.condominio.jockey.services.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.condominio.jockey.beans.Usuario;
import com.condominio.jockey.exception.UserNotFoundException;
import com.condominio.jockey.repository.UsuarioRepository;
import com.condominio.jockey.repository.implement.CustomRespositoryImpl;
import com.condominio.jockey.services.UsuarioServices;

@Service("usuarioService")
@Transactional
public class UsuarioServicesImpl implements UsuarioServices {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private CustomRespositoryImpl customImpl;

	@Override
	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	@Override
	public Usuario findById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		Usuario usuario = mongoOperations.findOne(query, Usuario.class);
		Optional<Usuario> optional = Optional.ofNullable(usuario);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new UserNotFoundException(id);
		}
	}

	@Override
	public Usuario guardarUsuario(Usuario usuario) {
		usuarioRepository.save(usuario);
		Query query = new Query(Criteria.where("_id").is(usuario.getId()));
		return mongoOperations.findOne(query, Usuario.class);
	}

	@Override
	public void actualizarUsuario(Usuario usuario) {
		customImpl.updateUsuario(usuario);
	}

	@Override
	public void eliminarUsuario(String id) {
		customImpl.eliminarUsuario(id);
	}
}
