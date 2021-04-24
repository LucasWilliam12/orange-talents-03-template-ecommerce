package br.com.zupacademy.lucas.treinomercadolivre.controllers.validators;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.zupacademy.lucas.treinomercadolivre.models.Usuario;
import br.com.zupacademy.lucas.treinomercadolivre.repositories.UsuarioRepository;
import br.com.zupacademy.lucas.treinomercadolivre.requests.NovoUsuarioRequest;

@Component
public class ProibeUsuarioComEmailDuplicadoValidator implements Validator{
	
	private UsuarioRepository usuarioRepository;
	
	public ProibeUsuarioComEmailDuplicadoValidator(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return NovoUsuarioRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(errors.hasErrors()) {
			return;
		}
		
		NovoUsuarioRequest request = (NovoUsuarioRequest) target;
		
		Optional<Usuario> consultaEmailUnico = usuarioRepository.findByEmail(request.getEmail());
		
		if(consultaEmailUnico.isPresent()) {
			errors.rejectValue("email", null, "Já existe usuario com o mesmo email cadastrado");
		}
		
	}

}
