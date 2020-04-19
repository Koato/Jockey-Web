package com.condominio.jockey.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.condominio.jockey.beans.Usuario;
import com.condominio.jockey.exception.UserNotFoundException;
import com.condominio.jockey.services.UsuarioServices;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {
	@Autowired
	private UsuarioServices usuarioServices;

	private Usuario usuario;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Usuario> usuarioById(@PathVariable String id) {
		try {
			usuario = usuarioServices.findById(id);
			return ResponseEntity.ok(usuario);
		} catch (UserNotFoundException e) {
			usuario = null;
		}
		return ResponseEntity.notFound().build();
	}

//	asignar que roles pueden acceder 
//	@Secured("Administrador")
	@GetMapping
	public ResponseEntity<List<Usuario>> usuarioById() {
		return ResponseEntity.ok(usuarioServices.findAll());
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteUsuario(@PathVariable String id) {
		usuarioServices.eliminarUsuario(id);
		return ResponseEntity.ok().build();
	}

	@PostMapping
	public ResponseEntity<Usuario> guardarUsuario(@RequestBody @Valid Usuario usuario) {
		return ResponseEntity.ok(usuarioServices.guardarUsuario(usuario));
	}

	@PutMapping
	public ResponseEntity<Void> actualizarUsuario(@RequestBody @Valid Usuario usuario) {
		usuarioServices.actualizarUsuario(usuario);
		return ResponseEntity.ok().build();
	}
}