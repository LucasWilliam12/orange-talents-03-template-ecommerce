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
}
