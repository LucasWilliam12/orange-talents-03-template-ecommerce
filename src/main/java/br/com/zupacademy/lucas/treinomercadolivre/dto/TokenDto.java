package br.com.zupacademy.lucas.treinomercadolivre.dto;

public class TokenDto {
	
	// Atributos
	private String tipo;
	private String token;
	
	// Construtores
	
	@Deprecated
	public TokenDto() {
	}
	
	public TokenDto(String tipo, String token) {
		this.tipo = tipo;
		this.token = token;
	}

	// Getters
	public String getTipo() {
		return tipo;
	}

	public String getToken() {
		return token;
	}
	
}
