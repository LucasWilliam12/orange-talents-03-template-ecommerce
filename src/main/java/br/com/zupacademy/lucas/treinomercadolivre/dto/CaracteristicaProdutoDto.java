package br.com.zupacademy.lucas.treinomercadolivre.dto;

import br.com.zupacademy.lucas.treinomercadolivre.models.CaracteristicaProduto;

public class CaracteristicaProdutoDto {
	
	private String nome;
	private String descricao;
	
	public CaracteristicaProdutoDto(CaracteristicaProduto caracteristica) {
		this.nome = caracteristica.getNome();
		this.descricao = caracteristica.getDescricao();
	}
	
	public String getNome() {
		return nome;
	}
	public String getDescricao() {
		return descricao;
	}
	
}
