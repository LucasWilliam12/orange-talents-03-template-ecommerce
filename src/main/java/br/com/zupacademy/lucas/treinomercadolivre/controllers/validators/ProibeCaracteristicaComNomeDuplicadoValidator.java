package br.com.zupacademy.lucas.treinomercadolivre.controllers.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.zupacademy.lucas.treinomercadolivre.requests.NovoProdutoRequest;

public class ProibeCaracteristicaComNomeDuplicadoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return NovoProdutoRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		if(errors.hasErrors()) {
			return ;
		}
		
		NovoProdutoRequest request = (NovoProdutoRequest) target;
		
		if(request.temCaracteristicasIguais()) {
			errors.reject("caracteristicas", null, "Você tem caracteristicas duplicadas");
		}
		
	}

}
