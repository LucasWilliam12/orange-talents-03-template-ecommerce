package br.com.zupacademy.lucas.treinomercadolivre.requests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.zupacademy.lucas.treinomercadolivre.controllers.validators.ExistsData;
import br.com.zupacademy.lucas.treinomercadolivre.dto.enumns.GatewayPagamento;
import br.com.zupacademy.lucas.treinomercadolivre.models.Produto;

public class ComprarRequest {

	// Atributos
	@NotNull
	@ExistsData(domainClass = Produto.class, fieldName = "id")
	private Long idProduto;
	@NotNull @Positive
	private int quantidade;
	@NotNull
	private GatewayPagamento gateway;
	
	// Construtores
	public ComprarRequest(Long idProduto, @NotNull @Positive int quantidade,@NotNull GatewayPagamento gateway) {
		this.idProduto = idProduto;
		this.quantidade = quantidade;
		this.gateway = gateway;
	}
	
	// Getters
	public Long getIdProduto() {
		return idProduto;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public GatewayPagamento getGateway() {
		return gateway;
	}

}
