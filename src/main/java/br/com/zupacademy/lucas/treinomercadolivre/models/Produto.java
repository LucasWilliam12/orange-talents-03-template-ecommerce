package br.com.zupacademy.lucas.treinomercadolivre.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.zupacademy.lucas.treinomercadolivre.dto.CaracteristicaProdutoDto;
import br.com.zupacademy.lucas.treinomercadolivre.requests.CaracteristicasRequest;

@Entity
@Table(name = "produtos")
public class Produto {
	
	// Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String nome;
	@NotNull @Positive
	private BigDecimal valor;
	@NotNull @Positive
	private int quantidade;
	@NotBlank @Length(max = 1000)
	private String descricao;
	@NotNull @Valid
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;
	@OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
	private Set<CaracteristicaProduto> caracteristicas = new HashSet<>();
	@ManyToOne
	private Usuario dono;
	private LocalDate instante = LocalDate.now();
	@OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
	private Set<ImagemProduto> imagens = new HashSet<>();
	@JsonIgnore
	@OneToMany(mappedBy = "produto")
	private List<Opiniao> opnioes = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy = "produto")
	@OrderBy("titulo asc")
	private SortedSet<Pergunta> perguntas = new TreeSet<>();
	@OneToMany(mappedBy = "produto")
	private List<Compra> compra = new ArrayList<>();
	
	// Construtores
	@Deprecated
	public Produto() {
	}
	
	public Produto(@NotBlank String nome, @NotNull @Positive BigDecimal valor,
			@NotNull @Positive int quantidade, @NotBlank @Length(max = 1000) String descricao,
			@NotNull @Valid Categoria categoria, Usuario dono, @Valid @Size(min = 3) Collection<CaracteristicasRequest> caracteristicas) {
		this.nome = nome;
		this.valor = valor;
		this.quantidade = quantidade;
		this.descricao = descricao;
		this.categoria = categoria;
		this.dono = dono;
		Set<CaracteristicaProduto> novasCaracteristicas = caracteristicas
				.stream().map(caracteristica -> caracteristica.toModel(this))
				.collect(Collectors.toSet());
		this.caracteristicas.addAll(novasCaracteristicas);
		Assert.isTrue(this.caracteristicas.size() >= 3, "Todo produto precisa ter no mínimo 3 ou mais caracteristicas");

	}
	
	// Getters
	public Long getId() {
		return id;
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
	public Categoria getCategoria() {
		return categoria;
	}
	public Usuario getDono() {
		return dono;
	}
	public Set<CaracteristicaProduto> getCaracteristicas() {
		return caracteristicas;
	}
	public LocalDate getInstante() {
		return instante;
	}

	public void associaImagens(Set<String> links) {
		Set<ImagemProduto> imagens =links.stream().map(link -> new ImagemProduto(this, link)).collect(Collectors.toSet());
		this.imagens.addAll(imagens);
		
	}
	
	public Set<ImagemProduto> getImagens() {
		return imagens;
	}

	public List<Opiniao> getOpnioes() {
		return opnioes;
	}
	
	public SortedSet<Pergunta> getPerguntas() {
		return perguntas;
	}

	public boolean pertenceAoUsuario(Usuario dono2) {
		return this.dono.equals(dono2);
	}

	public List<Compra> getCompra() {
		return compra;
	}
	
	public Set<CaracteristicaProdutoDto> converteCaracteristicasEmDto(Function<CaracteristicaProduto, CaracteristicaProdutoDto> funcao) {
		return this.caracteristicas.stream().map(funcao).collect(Collectors.toSet());
	}

	public <T> Set<T> converteLinksEmStrings(Function<ImagemProduto, T> funcao) {
		return this.imagens.stream().map(funcao).collect(Collectors.toSet());
	}

	public <T extends Comparable<T> > SortedSet<T> convertePerguntasEmString(Function<Pergunta, T> funcao) {
		return this.perguntas.stream().map(funcao).collect(Collectors.toCollection(TreeSet::new));
	}
	
	public <T> Set<T> converteOpinioes(Function<Opiniao, T> funcao) {
		return this.opnioes.stream().map(funcao).collect(Collectors.toSet());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((dono == null) ? 0 : dono.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Produto other = (Produto) obj;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (dono == null) {
			if (other.dono != null)
				return false;
		} else if (!dono.equals(other.dono))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	public boolean abate(int quantidade) {
		
		Assert.isTrue(quantidade > 0, "A quantidade não pode ser menor que 0");
		
		if(quantidade <= this.quantidade) {
			this.quantidade-=quantidade;
			return true;
		}
		
		return false;
	}

}
