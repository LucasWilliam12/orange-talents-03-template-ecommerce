package br.com.zupacademy.lucas.treinomercadolivre.requests;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zupacademy.lucas.treinomercadolivre.models.CaracteristicaProduto;
import br.com.zupacademy.lucas.treinomercadolivre.models.Produto;

public class CaracteristicasRequest {
	
	// Atributos
	@NotBlank
	private String nome;
	@NotBlank
	private String descricao;
	
	// Construtores
	public CaracteristicasRequest(@NotBlank String nome, @NotBlank String descricao) {
		this.nome = nome;
		this.descricao = descricao;
	}
	
	// Getters
	public String getNome() {
		return nome;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public CaracteristicaProduto toModel(@NotNull @Valid Produto produto) {
		return new CaracteristicaProduto(nome, descricao, produto);
	}
}
