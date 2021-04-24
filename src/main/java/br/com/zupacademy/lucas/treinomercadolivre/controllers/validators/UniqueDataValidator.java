package br.com.zupacademy.lucas.treinomercadolivre.controllers.validators;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.Assert;

public class UniqueDataValidator implements ConstraintValidator<UniqueData, Object>{

	@PersistenceContext
	private EntityManager em;
	
	private String field;
	private Class<?> klass;
	private String message;
	
	@Override
	public void initialize(UniqueData constraint) {
		this.field = constraint.fieldName();
		this.klass = constraint.domainClass();
		this.message = constraint.message();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		Query query = em.createQuery("SELECT 1 FROM "+ klass.getSimpleName() +" WHERE "+field+"=:value");
		query.setParameter("value", value);
		List<?> list = query.getResultList();
		
		Assert.state(list.size() <= 1, "Foi encontrado mais de um(a) "+klass.getSimpleName()+"com o mesmo atributo "+field);
		
		if(list.size() >= 1) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
		}
		
		return list.isEmpty();
	}

}
