package br.com.zupacademy.lucas.treinomercadolivre.validators;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.stream.Stream;

import br.com.zupacademy.lucas.treinomercadolivre.controllers.validators.ProibeUsuarioComEmailDuplicadoValidator;
import br.com.zupacademy.lucas.treinomercadolivre.models.SenhaLimpa;
import br.com.zupacademy.lucas.treinomercadolivre.models.Usuario;
import br.com.zupacademy.lucas.treinomercadolivre.repositories.UsuarioRepository;
import br.com.zupacademy.lucas.treinomercadolivre.requests.NovoUsuarioRequest;

public class ProibeUsuarioComEmailDuplicadoValidatorTeste {
	
	@DisplayName("Deveria lidar com email duplicado")
	@ParameterizedTest
	@MethodSource("geradorTeste1")
	void teste1(Optional<Usuario> possivelUsuario, boolean esperado) {
		UsuarioRepository usuarioRepository = Mockito.mock(UsuarioRepository.class);
		ProibeUsuarioComEmailDuplicadoValidator validator = new ProibeUsuarioComEmailDuplicadoValidator(usuarioRepository);
		Object target = new NovoUsuarioRequest("email@email.com.br", "senhaa");
		Errors erros = new BeanPropertyBindingResult(target, "teste");
		Mockito.when(usuarioRepository.findByEmail("email@email.com.br")).thenReturn(possivelUsuario);
		validator.validate(target, erros);
		
		Assertions.assertEquals(esperado, erros.hasFieldErrors("email"));
	}
	
	private static Stream<Arguments> geradorTeste1(){
		Optional<Usuario> usuario = Optional.of(new Usuario("email@email.com.br", new SenhaLimpa("senhaa")));
		return Stream.of(Arguments.of(usuario, true), Arguments.of(Optional.empty(), false));
	}
}
