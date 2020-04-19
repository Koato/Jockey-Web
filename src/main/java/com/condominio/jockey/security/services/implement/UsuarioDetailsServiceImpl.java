package com.condominio.jockey.security.services.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.condominio.jockey.beans.Usuario;
import com.condominio.jockey.repository.UsuarioRepository;
import com.condominio.jockey.security.services.PersonalizacionUsuarioDetalle;

@Service
@Transactional
public class UsuarioDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String alias) {
		Usuario usuario = usuarioRepository.findFirstByAlias(alias);
		if (usuario == null) {
			throw new UsernameNotFoundException("El usuario '" + alias + "' no existe");
		}
//		Asignacion de roles
		List<GrantedAuthority> userAuthorities = new ArrayList<>();
//		for (String rol : usuario.getRoles()) {
//			userAuthorities.add(new SimpleGrantedAuthority(rol));
		userAuthorities.add(new SimpleGrantedAuthority("Admin"));
//		}
		PersonalizacionUsuarioDetalle customUserDetail = new PersonalizacionUsuarioDetalle();
		customUserDetail.setUsuario(usuario);
		customUserDetail.setAuthorities(userAuthorities);
		return customUserDetail;
	}
}
