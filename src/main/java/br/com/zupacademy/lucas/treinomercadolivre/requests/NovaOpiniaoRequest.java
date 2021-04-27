package br.com.zupacademy.lucas.treinomercadolivre.requests;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zupacademy.lucas.treinomercadolivre.models.Opiniao;
import br.com.zupacademy.lucas.treinomercadolivre.models.Produto;
import br.com.zupacademy.lucas.treinomercadolivre.models.Usuario;

public class NovaOpiniaoRequest {
	
	@NotNull
	@Max(5) @Min(1)
	private int nota;
	@NotBlank
	private String titulo;
	@NotBlank
	private String descricao;

	@Deprecated
	public NovaOpiniaoRequest() {
	}

	public NovaOpiniaoRequest(@NotNull @Max(5) @Min(1) int nota, @NotBlank String titulo,
			@NotBlank String descricao) {
		this.nota = nota;
		this.titulo = titulo;
		this.descricao = descricao;
	}

	public int getNota() {
		return nota;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public Opiniao toModel(@NotNull @Valid Usuario consumidor, @NotNull @Valid Produto produto) {
		return new Opiniao(titulo, descricao, nota, consumidor, produto);
	}
	
}
