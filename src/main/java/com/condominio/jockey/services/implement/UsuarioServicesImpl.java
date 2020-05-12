package com.condominio.jockey.services.implement;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.condominio.jockey.beans.Usuario;
import com.condominio.jockey.exception.UserNotFoundException;
import com.condominio.jockey.repository.UsuarioRepository;
import com.condominio.jockey.services.UsuarioServices;
import com.mongodb.client.result.UpdateResult;

@Service("usuarioService")
public class UsuarioServicesImpl implements UsuarioServices {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private MongoOperations mongoOperations;

	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> findAll(Pageable pageable) {
		return usuarioRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findById(String id) {
		Optional<Usuario> usuario = obtenerUsuario(id);
		if (usuario.isPresent()) {
			return usuario.get();
		} else {
			throw new UserNotFoundException(id);
		}
	}

	@Override
	@Transactional
	public Usuario guardarUsuario(Usuario usuario) {
		usuario.setClave(encriptarClave(usuario.getClave()));
		usuarioRepository.save(usuario);
		return obtenerUsuario(usuario.getId()).get();
	}

	@Override
	@Transactional
	public Usuario actualizarUsuario(Usuario usuario) {
		Query query = new Query(Criteria.where("_id").is(usuario.getId()));
		Update update = new Update();
		update.set("alias", usuario.getAlias());
		update.set("clave", encriptarClave(usuario.getClave()));
		update.set("tiempoAcceso", usuario.getTiempoAcceso());
		update.set("estado", usuario.isEstado());
		update.set("roles", usuario.getRoles());
		UpdateResult result = mongoOperations.updateFirst(query, update, Usuario.class);
		if (result.getMatchedCount() == 0) {
			throw new UserNotFoundException(usuario.getId());
		}
		return obtenerUsuario(usuario.getId()).get();
	}

	@Override
	@Transactional
	public Usuario eliminarUsuario(String id) {
		Optional<Usuario> usuario = obtenerUsuario(id);
		if (usuario.isPresent()) {
			long result = mongoOperations.remove(usuario.get()).getDeletedCount();
			if (result == 0) {
				throw new UserNotFoundException(id);
			}
			return usuario.get();
		} else {
			throw new UserNotFoundException(id);
		}
	}

	private Optional<Usuario> obtenerUsuario(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return Optional.ofNullable(mongoOperations.findOne(query, Usuario.class));
	}

	private String encriptarClave(String clave) {
		return new BCryptPasswordEncoder().encode(clave);
	}
}