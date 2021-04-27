package br.com.zupacademy.lucas.treinomercadolivre.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Opiniao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String titulo;
	@NotBlank
	private String descricao;
	@NotNull @Max(5) @Min(1)
	private int nota;
	@ManyToOne
	@Valid
	private Usuario consumidor;
	@ManyToOne
	@Valid
	private Produto produto;
	
	@Deprecated
	public Opiniao() {
	}

	public Opiniao(@NotBlank String titulo, @NotBlank String descricao, @NotNull @Max(5) @Min(1) int nota,
			@Valid Usuario consumidor, @Valid Produto produto) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.nota = nota;
		this.consumidor = consumidor;
		this.produto = produto;
	}

	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public int getNota() {
		return nota;
	}

	public Usuario getConsumidor() {
		return consumidor;
	}
	
	public Produto getProduto() {
		return produto;
	}

}
