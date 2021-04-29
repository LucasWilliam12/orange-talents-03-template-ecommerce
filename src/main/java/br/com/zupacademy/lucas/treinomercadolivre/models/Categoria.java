package br.com.zupacademy.lucas.treinomercadolivre.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "categorias")
public class Categoria {
	
	// Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true, nullable = false)
	@NotBlank @NotNull
	private String nome;
	@ManyToOne
	private Categoria categoriaMae;
	@JsonIgnore
	@OneToMany(mappedBy = "categoria")
	private List<Produto> produtos = new ArrayList<>();
	
	// Construtores
	public Categoria() {
	}
	
	// Getters e Setters
	public void setNome(@NotBlank @NotNull String nome) {
		Assert.isTrue(StringUtils.hasLength(nome), "o nome não pode ser em branco");
		Assert.notNull(nome, "o nome não pode ser nulo");
		this.nome = nome;
	}
	
	public void setCategoriaMae(Categoria categoriaMae) {
		this.categoriaMae = categoriaMae;
	}

	public Long getId() {
		return this.id;
	}

	public String getNome() {
		return this.nome;
	}

	public Categoria getCategoriaMae() {
		return this.categoriaMae;
	}
	
	public void addProduto(Produto produto) {
		this.produtos.add(produto);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoriaMae == null) ? 0 : categoriaMae.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Categoria other = (Categoria) obj;
		if (categoriaMae == null) {
			if (other.categoriaMae != null)
				return false;
		} else if (!categoriaMae.equals(other.categoriaMae))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
}
