package br.com.zupacademy.lucas.treinomercadolivre.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.zupacademy.lucas.treinomercadolivre.models.Usuario;
import br.com.zupacademy.lucas.treinomercadolivre.repositories.UsuarioRepository;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UsuarioRepository userRepo;
	@Autowired
	private UserDetailsMapper userDetailsMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> user = userRepo.findByEmail(username);
		
		if(user.isEmpty()) {
			throw new UsernameNotFoundException("Não foi possível encontrar usuário com email: "+ username);
		}
		return userDetailsMapper.map(user.get());
	}

}
