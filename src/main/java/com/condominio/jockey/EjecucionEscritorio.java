package com.condominio.jockey;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.condominio.jockey.beans.Usuario;
import com.condominio.jockey.repository.UsuarioRepository;
import com.condominio.jockey.services.UsuarioServices;

@SpringBootApplication
public class EjecucionEscritorio implements CommandLineRunner {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	UsuarioServices usuarioServices;

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
		administrador.setClave("$2a$10$PVm.mQWtWOBJ44rCFff4N.8RofPKrTQuhEiNPn6IXrRE7NLdk6ojW");
		administrador.setTiempoAcceso("13/04/2020");
		administrador.setEstado(true);
		administrador.setRoles(Arrays.asList("Administrador", "Usuario"));

		Usuario presidente = new Usuario();
		presidente.setId("2");
		presidente.setAlias("Presidente");
		presidente.setClave("$2a$10$OFEejm.Rkd2RtbU3n9pHzeu6dXfb029Whs7BX88u/1DuEcbCbC1q.");
		presidente.setTiempoAcceso("14/04/2020");
		presidente.setEstado(true);
		presidente.setRoles(Arrays.asList("Usuario"));

		Usuario tesorero = new Usuario();
		tesorero.setId("3");
		tesorero.setAlias("Tesorero");
		tesorero.setClave("$2a$10$pzI9vtVI6vos8brPgZh5iOBJSokz4vPJ5yqbT0Djkv8OVPj/dwORm");
		tesorero.setTiempoAcceso("15/04/2020");
		tesorero.setEstado(false);
		tesorero.setRoles(Arrays.asList("Usuario"));

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
		usuarioServices.actualizarUsuario(tesorero);
		System.err.println("Documento modificado");
	}

	public void extraerPrimero() {
		System.err.println("Extrayendo al primer registro...");
		Usuario usuario = usuarioRepository.findFirstByAlias("Tesorero");
		System.err.println(usuario);
	}

	public void eliminarSegundo() {
		System.err.println("Elimando al segundo registro");
		usuarioServices.eliminarUsuario("2");
	}
}