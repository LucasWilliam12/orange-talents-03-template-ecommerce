package br.com.zupacademy.lucas.treinomercadolivre.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Pergunta {
	
	// Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String titulo;
	private LocalDate instante = LocalDate.now();
	@NotNull @Valid
	@ManyToOne
	private Usuario consumidor;
	@NotNull @Valid
	@ManyToOne
	private Produto produto;
	
	// Construtores
	@Deprecated
	public Pergunta() {
	}

	public Pergunta(@NotBlank String titulo, @NotNull @Valid Usuario consumidor,
			@NotNull @Valid Produto produto) {
		this.titulo = titulo;
		this.consumidor = consumidor;
		this.produto = produto;
	}

	// Getters
	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public LocalDate getInstante() {
		return instante;
	}

	public Usuario getConsumidor() {
		return consumidor;
	}

	public Produto getProduto() {
		return produto;
	}
	
}
