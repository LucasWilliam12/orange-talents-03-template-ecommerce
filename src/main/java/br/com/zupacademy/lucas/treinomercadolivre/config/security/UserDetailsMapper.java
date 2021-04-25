package br.com.zupacademy.lucas.treinomercadolivre.config.security;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Interfae que você pode implementar para retornar um {@link UserDetails}
 * em função do objeto que representa sua entidade de usuario
 * @author Lucas
 *
 */
public interface UserDetailsMapper{

	/**
	 * 
	 * @param shouldBeASystemUser um objeto que deveria representar seu usuário logado
	 * @return
	 */
	UserDetails map(Object shouldBeASystemUser);
	
}
