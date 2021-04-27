package br.com.zupacademy.lucas.treinomercadolivre.dto;

import java.time.LocalDate;

import br.com.zupacademy.lucas.treinomercadolivre.models.Pergunta;

public class PerguntaDto {
	
	// Atributos
	private String titulo;
	private LocalDate instante;
	private UsuarioDto consumidor;
	private ProdutoDto produto;
	
	// Construtores
	public PerguntaDto(Pergunta pergunta) {
		this.titulo = pergunta.getTitulo();
		this.instante = pergunta.getInstante();
		this.consumidor = new UsuarioDto(pergunta.getConsumidor());
		this.produto = new ProdutoDto(pergunta.getProduto());
	}

	// Getters
	public String getTitulo() {
		return titulo;
	}

	public LocalDate getInstante() {
		return instante;
	}

	public UsuarioDto getConsumidor() {
		return consumidor;
	}

	public ProdutoDto getProduto() {
		return produto;
	}
	
}
