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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((consumidor == null) ? 0 : consumidor.hashCode());
		result = prime * result + nota;
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Opiniao other = (Opiniao) obj;
		if (consumidor == null) {
			if (other.consumidor != null)
				return false;
		} else if (!consumidor.equals(other.consumidor))
			return false;
		if (nota != other.nota)
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

}
