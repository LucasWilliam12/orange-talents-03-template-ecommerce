package br.com.zupacademy.lucas.treinomercadolivre.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.zupacademy.lucas.treinomercadolivre.controllers.exceptions.ObjectNotFoundException;

@RestControllerAdvice
public class ValidationErrorHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorsOutputDto> handleValidationError(MethodArgumentNotValidException exception) {
		List<ObjectError> globalErrors = exception.getBindingResult().getGlobalErrors();
		List<FieldError> fieldsErrors = exception.getBindingResult().getFieldErrors();
		
		ValidationErrorsOutputDto errors = buildValidationErrors(globalErrors, fieldsErrors);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<ValidationErrorsOutputDto> handleObjectNotFoundException(ObjectNotFoundException exception) {

		ValidationErrorsOutputDto errors = new ValidationErrorsOutputDto();
		errors.addFieldError(exception.getField(), messageSource.getMessage(null, null, exception.getMessage(),  LocaleContextHolder.getLocale()));
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
	}
	

	private ValidationErrorsOutputDto buildValidationErrors(List<ObjectError> globalErrors,
			List<FieldError> fieldsErrors) {
		
		ValidationErrorsOutputDto validationErrors = new ValidationErrorsOutputDto();
		
		globalErrors.forEach(error -> validationErrors.addError(getErrorMessage(error)));
		fieldsErrors.forEach(error -> {
			String errorMessage = getErrorMessage(error);
			validationErrors.addFieldError(error.getField(), errorMessage);
		});
		return validationErrors;
	}


	private String getErrorMessage(ObjectError error) {
		return messageSource.getMessage(error, LocaleContextHolder.getLocale());
	}
}
