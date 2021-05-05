package br.com.zupacademy.lucas.treinomercadolivre.models;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.util.Assert;

import br.com.zupacademy.lucas.treinomercadolivre.dto.enumns.GatewayPagamento;
import br.com.zupacademy.lucas.treinomercadolivre.dto.enumns.StatusCompra;
import br.com.zupacademy.lucas.treinomercadolivre.requests.RetornoGatewayPagamento;

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
	@OneToMany(mappedBy = "compra", cascade = CascadeType.MERGE)
	private Set<Transacao> transacoes = new HashSet<>();
	
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

	public void adicionaTransacao(@Valid RetornoGatewayPagamento request) {

		Transacao novaTransacao = request.toTransacao(this);
		
		Assert.isTrue(!this.transacoes.contains(novaTransacao), "Já existe uma transacao igual essa sendo processada");
		
		Assert.isTrue(this.transacoesConcoluidasComSucesso().isEmpty(), "Essa compra já foi concluída com sucesso!");
		
		this.transacoes.add(request.toTransacao(this));
	}
	
	
	private Set<Transacao> transacoesConcoluidasComSucesso(){
		Set<Transacao> transacoesComSucesso = this.transacoes.stream()
				.filter(transacao -> transacao.concluidaComSucesso())
				.collect(Collectors.toSet());
		
		Assert.isTrue(transacoesComSucesso.size() <= 1, "tem mais uma transacao concluida com sucesso");
		
		return transacoesComSucesso;
	}
	
	public boolean processadaComSucesso() {
		return !this.transacoesConcoluidasComSucesso().isEmpty();
	}

	public Usuario getDonoProduto() {
		return produto.getDono();
	}
	
}