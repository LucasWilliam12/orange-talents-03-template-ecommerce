package br.com.zupacademy.lucas.treinomercadolivre.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import br.com.zupacademy.lucas.treinomercadolivre.models.CaracteristicaProduto;
import br.com.zupacademy.lucas.treinomercadolivre.models.ImagemProduto;
import br.com.zupacademy.lucas.treinomercadolivre.models.Produto;

public class ProdutoDto {
	
	private String nome;
	private String descricao;
	private BigDecimal valor;
	private int quantidade;
	private LocalDate instante;
	private Set<CaracteristicaProduto> caracteristicas = new HashSet<>();
	private UsuarioDto dono;
	private NovaCategoriaDto categoria;
	private Set<ImagemProduto> imagens;
	
	
	public ProdutoDto(Produto produto) {
		this.nome = produto.getNome();
		this.descricao = produto.getDescricao();
		this.valor = produto.getValor();
		this.quantidade = produto.getQuantidade();
		this.instante = produto.getInstante();
		this.caracteristicas = produto.getCaracteristicas();
		this.dono = new UsuarioDto(produto.getDono());
		this.categoria = new NovaCategoriaDto(produto.getCategoria());
		this.imagens = produto.getImagens();
	}

	public String getNome() {
		return nome;
	}


	public String getDescricao() {
		return descricao;
	}


	public BigDecimal getValor() {
		return valor;
	}


	public int getQuantidade() {
		return quantidade;
	}


	public LocalDate getInstante() {
		return instante;
	}


	public Set<CaracteristicaProduto> getCaracteristicas() {
		return caracteristicas;
	}


	public UsuarioDto getDono() {
		return dono;
	}


	public NovaCategoriaDto getCategoria() {
		return categoria;
	}
	
	public Set<ImagemProduto> getImagens() {
		return imagens;
	}
}
