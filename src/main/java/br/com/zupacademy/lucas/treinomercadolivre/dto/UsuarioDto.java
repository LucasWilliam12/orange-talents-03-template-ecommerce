package br.com.zupacademy.lucas.treinomercadolivre.dto;

import br.com.zupacademy.lucas.treinomercadolivre.models.Usuario;

public class UsuarioDto {
	
	// Atributos
	private String email;

	// Construtores
	public UsuarioDto(Usuario usuario) {
		this.email = usuario.getEmail();
	}
	
	// Getters
	public String getEmail() {
		return email;
	}
}
