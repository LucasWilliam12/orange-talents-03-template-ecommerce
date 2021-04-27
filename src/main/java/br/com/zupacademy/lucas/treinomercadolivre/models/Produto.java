package br.com.zupacademy.lucas.treinomercadolivre.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
	private List<Opiniao> opnioes = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
	private List<Pergunta> perguntas = new ArrayList<>();
	
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
	
	public List<Pergunta> getPerguntas() {
		return perguntas;
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", nome=" + nome + ", valor=" + valor + ", quantidade=" + quantidade
				+ ", descricao=" + descricao + ", categoria=" + categoria + ", caracteristicas=" + caracteristicas
				+ ", dono=" + dono + ", instante=" + instante + ", imagens=" + imagens + "]";
	}

	public boolean pertenceAoUsuario(Usuario dono2) {
		return this.dono.equals(dono2);
	}
	
	
}
