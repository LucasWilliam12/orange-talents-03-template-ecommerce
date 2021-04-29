package br.com.zupacademy.lucas.treinomercadolivre.dto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.SortedSet;

import br.com.zupacademy.lucas.treinomercadolivre.models.Produto;

public class DetalheProdutoDto {
	
	private String nome;
	private String descricao;
	private BigDecimal valor;
	private double mediaNotas;
	private int totalOpinioes;
	private Set<CaracteristicaProdutoDto> caracteristicas;
	private Set<String> links;
	private SortedSet<String> perguntas;
	private Set<Map<String, String>> opinioes;
	
	
	public DetalheProdutoDto(Produto produto) {
		this.nome = produto.getNome();
		this.descricao = produto.getDescricao();
		this.valor = produto.getValor();
		this.caracteristicas = produto.converteCaracteristicasEmDto(x -> new CaracteristicaProdutoDto(x));
		this.links = produto.converteLinksEmStrings(x -> x.getLink());
		this.perguntas = produto.convertePerguntasEmString(x -> x.getTitulo());
		this.opinioes = produto.converteOpinioes(opniao -> {
			return Map.of("titulo", opniao.getTitulo(), "descricao", opniao.getDescricao());
		});
		Set<Integer> notas = produto.converteOpinioes(opiniao -> opiniao.getNota());
		OptionalDouble media = notas.stream().mapToInt(nota -> nota).average();
		this.totalOpinioes = notas.size();
		this.mediaNotas = media.orElseGet(() -> 0.0);
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
	
	public int getTotalOpinioes() {
		return totalOpinioes;
	}
	
	public double getMediaNotas() {
		return mediaNotas;
	}

	public Set<CaracteristicaProdutoDto> getCaracteristicas() {
		return caracteristicas;
	}
	
	public Set<String> getLinks() {
		return links;
	}
	
	public SortedSet<String> getPerguntas() {
		return perguntas;
	}
	
	public Set<Map<String, String>> getOpinioes() {
		return opinioes;
	}
	
}
