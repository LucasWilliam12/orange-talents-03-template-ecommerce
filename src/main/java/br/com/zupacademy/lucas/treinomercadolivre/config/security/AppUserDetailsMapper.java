package br.com.zupacademy.lucas.treinomercadolivre.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import br.com.zupacademy.lucas.treinomercadolivre.models.Usuario;

@Component
public class AppUserDetailsMapper implements UserDetailsMapper {

	@Override
	public UserDetails map(Object shouldBeASystemUser) {
		return new UsuarioLogado((Usuario)shouldBeASystemUser);
	}

}
