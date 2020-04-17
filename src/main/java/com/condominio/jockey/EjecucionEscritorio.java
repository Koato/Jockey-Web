package com.condominio.jockey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.condominio.jockey.beans.Usuario;
import com.condominio.jockey.repository.CustomRepository;
import com.condominio.jockey.repository.UsuarioRepository;

@SpringBootApplication
public class EjecucionEscritorio implements CommandLineRunner {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	CustomRepository customRepository;

	public static void main(String[] args) {
		SpringApplication.run(EjecucionEscritorio.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		eliminarTodo();
//		agregarDocumentos();
//		listarTodo();
//		listarTodoAdministrador();
//		actualizarPrimero();
//		extraerPrimero();
//		eliminarSegundo();
	}

	public void eliminarTodo() {
		System.err.println("Eliminando todos los documentos...");
		usuarioRepository.deleteAll();
	}

	public void agregarDocumentos() {
		System.err.println("Agregando documentos a usuario...");
		Usuario administrador = new Usuario();
		administrador.setId("1");
		administrador.setAlias("Administrador");
		administrador.setClave("ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f");
		administrador.setTiempoAcceso("13/04/2020");
		administrador.setEstado(true);

		Usuario presidente = new Usuario();
		presidente.setId("2");
		presidente.setAlias("Presidente");
		presidente.setClave("0729563253bc11cb72714d61132adfe7ba2346b581b02546c9ac4a65fc0c02d8");
		presidente.setTiempoAcceso("14/04/2020");
		presidente.setEstado(true);

		Usuario tesorero = new Usuario();
		tesorero.setId("3");
		tesorero.setAlias("Tesorero");
		tesorero.setClave("6b2a33f4d7ccddc176fdc65a2e6d9fdf39f161e5afeca296c50c3eea94d40924");
		tesorero.setTiempoAcceso("15/04/2020");
		tesorero.setEstado(false);

		usuarioRepository.insert(administrador);
		usuarioRepository.insert(presidente);
		usuarioRepository.insert(tesorero);
	}

	public void listarTodoAdministrador() {
		System.err.println("Listando a todos los administradores...");
		usuarioRepository.findByAlias("Administrador").forEach(admin -> System.err.println(admin));
	}

	public void listarTodo() {
		System.err.println("Listando toda la data...");
		usuarioRepository.findAll().forEach(usuario -> System.err.println(usuario));
	}

	public void actualizarPrimero() {
		System.err.println("Actualizando al primer registro");
		Usuario tesorero = new Usuario();
		tesorero.setId("3");
		tesorero.setAlias("Tesorero");
		tesorero.setClave("6b2a33f4d7ccddc176fdc65a2e6d9fdf39f161e5afeca296c50c3eea94d40924");
		tesorero.setTiempoAcceso("21/04/2020");
		tesorero.setEstado(false);
		long modificados = customRepository.updateUsuario(tesorero);
		System.err.println("Documentos modificados: " + modificados);
	}

	public void extraerPrimero() {
		System.err.println("Extrayendo al primer registro...");
		Usuario usuario = usuarioRepository.findFirstByAlias("Tesorero");
		System.err.println(usuario);
	}

	public void eliminarSegundo() {
		System.err.println("Elimando al segundo registro");
		customRepository.eliminarUsuario("2");
	}
}