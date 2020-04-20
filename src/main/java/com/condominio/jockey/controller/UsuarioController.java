package com.condominio.jockey.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
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

//indico quienes pueden consumir el servicio
@CrossOrigin(origins = { "http://localhost:3131" })
@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {
	@Autowired
	private UsuarioServices usuarioServices;

	private static final String RESPONSE_ERROR = "error";
	private static final String RESPONSE_MENSAJE = "mensaje";

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> usuario(@PathVariable String id) {
		Map<String, Object> response = new HashMap<>();
		Usuario usuario = null;
		try {
			usuario = usuarioServices.findById(id);
			return ResponseEntity.ok(usuario);
		} catch (UserNotFoundException e) {
			response.put(RESPONSE_ERROR, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (DataAccessException e) {
			response.put(RESPONSE_MENSAJE, "Error al realizar la consulta en la base de datos");
			response.put(RESPONSE_ERROR, e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	asignar que roles pueden acceder 
	@Secured("Administrador")
	@GetMapping
	public ResponseEntity<?> usuarios() {
		Map<String, Object> response = new HashMap<>();
		response.put("usuarios", usuarioServices.findAll());
		response.put("total", usuarioServices.findAll().stream().count());
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<?> insertarUsuario(@Valid @RequestBody Usuario usuario, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		Usuario usuarioNuevo = null;
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(error -> "El campo '" + error.getField() + "' " + error.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			usuarioNuevo = usuarioServices.guardarUsuario(usuario);
			response.put(RESPONSE_MENSAJE, "Ha sido insertado con éxito");
			response.put("usuario", usuarioNuevo);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put(RESPONSE_MENSAJE, "Error al insertarlo en la base de datos");
			response.put(RESPONSE_ERROR, e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/{id}/editar")
	public ResponseEntity<?> actualizarUsuario(@PathVariable String id, @Valid @RequestBody Usuario usuario,
			BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(error -> "El campo '" + error.getField() + "' " + error.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			usuario.setId(id);
			usuarioServices.actualizarUsuario(usuario);
			response.put(RESPONSE_MENSAJE, "Ha sido actualizado con éxito");
			response.put("usuario", usuario);
			return ResponseEntity.ok(response);
		} catch (UserNotFoundException e) {
			response.put(RESPONSE_ERROR, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (DataAccessException e) {
			response.put(RESPONSE_MENSAJE, "Error al actualizarlo en la base de datos");
			response.put(RESPONSE_ERROR, e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> eliminarUsuario(@PathVariable String id) {
		Map<String, Object> response = new HashMap<>();
		try {
			usuarioServices.eliminarUsuario(id);
			response.put(RESPONSE_MENSAJE, "Ha sido eliminado con éxito");
			return ResponseEntity.ok(response);
		} catch (UserNotFoundException e) {
			response.put(RESPONSE_ERROR, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (DataAccessException e) {
			response.put(RESPONSE_MENSAJE, "Error al eliminarlo en la base de datos");
			response.put(RESPONSE_ERROR, e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}