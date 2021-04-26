package br.com.zupacademy.lucas.treinomercadolivre.requests;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import br.com.zupacademy.lucas.treinomercadolivre.controllers.exceptions.ObjectNotFoundException;
import br.com.zupacademy.lucas.treinomercadolivre.controllers.validators.ExistsData;
import br.com.zupacademy.lucas.treinomercadolivre.models.Categoria;
import br.com.zupacademy.lucas.treinomercadolivre.models.Produto;
import br.com.zupacademy.lucas.treinomercadolivre.models.Usuario;
import br.com.zupacademy.lucas.treinomercadolivre.repositories.CategoriaRepository;

public class NovoProdutoRequest {
	
	// Atributos
	@NotBlank
	private String nome;
	@NotNull
	@Positive
	private BigDecimal valor;
	@NotNull
	@Positive
	private int quantidade;
	@NotBlank
	@Length(max = 1000)
	private String descricao;
	@NotNull
	@ExistsData(domainClass = Categoria.class, fieldName = "id")
	private Long idCategoria;
	@Valid
	@Size(min = 3)
	private List<CaracteristicasRequest> caracteristicas = new ArrayList<>();
	
	// Construtores
	public NovoProdutoRequest(@NotBlank String nome, @NotNull @Positive BigDecimal valor,
			@NotNull @Positive int quantidade, @NotBlank @Length(max = 1000) String descricao,
			@NotNull Long idCategoria, @Valid @Size(min = 3)List<CaracteristicasRequest> caracteristicas) {
		this.nome = nome;
		this.valor = valor;
		this.quantidade = quantidade;
		this.descricao = descricao;
		this.idCategoria = idCategoria;
		this.caracteristicas.addAll(caracteristicas);
	}
	
	public Produto toModel(CategoriaRepository categoriaRepo, Usuario dono) {
		
		Categoria categoria = categoriaRepo.findById(idCategoria).orElseThrow(() -> 
		new ObjectNotFoundException("Categoria não encontrada com o id informado: "+idCategoria));
		
		
		return new Produto(this.nome, this.valor, this.quantidade, this.descricao, categoria, dono, caracteristicas);
	}

	public String getNome() {
		return nome;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public String getDescricao() {
		return descricao;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public List<CaracteristicasRequest> getCaracteristicas() {
		return caracteristicas;
	}

	public boolean temCaracteristicasIguais() {
		HashSet<String> nomesIguais = new HashSet<>();
		for(CaracteristicasRequest caracteristica: caracteristicas) {
			if(!nomesIguais.add(caracteristica.getNome())) {
				return true;
			}
		}
		return false;
	}
	
}
