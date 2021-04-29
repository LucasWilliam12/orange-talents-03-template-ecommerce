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
public class Pergunta implements Comparable<Pergunta>{
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((consumidor == null) ? 0 : consumidor.hashCode());
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
		Pergunta other = (Pergunta) obj;
		if (consumidor == null) {
			if (other.consumidor != null)
				return false;
		} else if (!consumidor.equals(other.consumidor))
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

	@Override
	public int compareTo(Pergunta o) {
		return this.titulo.compareTo(o.getTitulo());
	}
	
}
