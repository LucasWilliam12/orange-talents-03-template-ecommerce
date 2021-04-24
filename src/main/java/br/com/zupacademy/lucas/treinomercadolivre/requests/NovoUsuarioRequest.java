package br.com.zupacademy.lucas.treinomercadolivre.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.zupacademy.lucas.treinomercadolivre.models.SenhaLimpa;
import br.com.zupacademy.lucas.treinomercadolivre.models.Usuario;

public class NovoUsuarioRequest {
	
	// Atributos
	@NotBlank @NotNull
	@Email
	private String email;
	@NotBlank @NotNull @Length(min = 6)
	private String senha;
	
	// Controllers
	public NovoUsuarioRequest(@NotBlank @NotNull @Email String email, @NotBlank @Length(min = 6) @NotNull String senha) {
		this.email = email;
		this.senha = senha;
	}

	public Usuario toModel() {
		return new Usuario(email, new SenhaLimpa(senha));
	}
	
	public String getEmail() {
		return email;
	}
	
}
