package br.com.zupacademy.lucas.treinomercadolivre.requests;

import javax.validation.constraints.NotBlank;

import br.com.zupacademy.lucas.treinomercadolivre.models.Pergunta;
import br.com.zupacademy.lucas.treinomercadolivre.models.Produto;
import br.com.zupacademy.lucas.treinomercadolivre.models.Usuario;

public class NovaPerguntaRequest {
	
	// Atributos
	@NotBlank
	private String titulo;
	
	// Construtores
	@Deprecated
	public NovaPerguntaRequest() {
	}

	public NovaPerguntaRequest(@NotBlank String titulo) {
		this.titulo = titulo;
	}
	
	// Getters
	public String getTitulo() {
		return titulo;
	}

	public Pergunta toModel(Produto produto, Usuario consumidor) {
		return new Pergunta(titulo, consumidor, produto);
	}
}
