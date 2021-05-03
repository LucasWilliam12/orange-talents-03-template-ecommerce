package br.com.zupacademy.lucas.treinomercadolivre.models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.zupacademy.lucas.treinomercadolivre.dto.enumns.GatewayPagamento;
import br.com.zupacademy.lucas.treinomercadolivre.dto.enumns.StatusCompra;

@Entity
@Table(name = "compras")
public class Compra {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@Positive
	@NotNull
	private int quantidade;
	@Enumerated
	@NotNull
	private GatewayPagamento gateway;
	@NotNull
	private BigDecimal valorAtualProduto;
	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Produto produto;
	@ManyToOne
	@JoinColumn(name = "comprador_id")
	private Usuario comprador;
	@Enumerated
	@NotNull
	private StatusCompra status;
	
	@Deprecated
	public Compra() {
	}

	public Compra(@Positive @NotNull int quantidade, Produto produto,
			Usuario comprador,@NotNull GatewayPagamento gateway) {
		this.quantidade = quantidade;
		this.valorAtualProduto = produto.getValor();
		this.produto = produto;
		this.comprador = comprador;
		this.gateway = gateway;
		this.status = StatusCompra.INICIADA;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public int getQuantidade() {
		return quantidade;
	}

	public BigDecimal getValorAtualProduto() {
		return valorAtualProduto;
	}

	public Produto getProduto() {
		return produto;
	}

	public Usuario getComprador() {
		return comprador;
	}
	
	public GatewayPagamento getGateway() {
		return gateway;
	}
	public StatusCompra getStatus() {
		return status;
	}
	
}