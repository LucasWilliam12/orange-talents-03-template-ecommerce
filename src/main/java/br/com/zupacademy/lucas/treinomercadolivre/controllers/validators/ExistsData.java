package br.com.zupacademy.lucas.treinomercadolivre.controllers.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.TYPE})
@Constraint(validatedBy = ExistsDataValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsData {
	
	String message () default "Valor não cadastrado.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	String fieldName();
	Class<?> domainClass();
	
}
