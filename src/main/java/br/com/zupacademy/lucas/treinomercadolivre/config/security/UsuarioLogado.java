package br.com.zupacademy.lucas.treinomercadolivre.config.security;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.zupacademy.lucas.treinomercadolivre.models.Usuario;

public class UsuarioLogado  implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	// Atributos
	private Usuario usuario;
	private User springUserDetails;
	
	// Construtores
	public UsuarioLogado(@NotNull @Valid Usuario usuario) {
		this.usuario = usuario;
		springUserDetails = new User(usuario.getEmail(), usuario.getSenha(), List.of());
	}
	
	// Getters
	public Usuario getUsuario() {
		return usuario;
	}

	public User getSpringUserDetails() {
		return springUserDetails;
	}

	// Contratos UserDetails
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.springUserDetails.getAuthorities();
	}

	@Override
	public String getPassword() {
		return this.springUserDetails.getPassword();
	}

	@Override
	public String getUsername() {
		return this.springUserDetails.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
