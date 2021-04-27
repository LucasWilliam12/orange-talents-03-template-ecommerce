package br.com.zupacademy.lucas.treinomercadolivre.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "usuarios")
public class Usuario {
	
	// Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank @NotNull @Email
	@Column(nullable = false, unique = true)
	private String email;
	@NotBlank @NotNull @Length(min = 6)
	@Column(nullable = false)
	private String senha;
	private LocalDate instante = LocalDate.now();
	@JsonIgnore
	@OneToMany(mappedBy = "dono")
	private List<Produto> produtos = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy = "consumidor")
	private List<Opiniao> opnioes = new ArrayList<>();
	
	// Construtores
	@Deprecated
	public Usuario() {
	}

	public Usuario(@NotBlank @NotNull @Email String email, @Valid @NotNull SenhaLimpa senhaLimpa) {
		Assert.isTrue(StringUtils.hasLength(email), "email não pode ser em branco");
		Assert.notNull(senhaLimpa, "o objeto do tipo senha limpa não pode ser nulo");
		
		this.email = email;
		this.senha = senhaLimpa.hash();
	}

	// Getters
	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	public LocalDate getInstante() {
		return instante;
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}
	
	public void addProduto(Produto produto) {
		this.produtos.add(produto);
	}

	public void addOpniao(Opiniao opniao) {
		this.opnioes.add(opniao);
	}
	
	public List<Opiniao> getOpnioes() {
		return opnioes;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Usuario other = (Usuario) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
