package br.com.zupacademy.lucas.treinomercadolivre.dto;

import br.com.zupacademy.lucas.treinomercadolivre.models.Opiniao;

public class OpiniaoDto {

	private String titulo;
	private String descricao;
	private int nota;
	private UsuarioDto consumidor;
	private ProdutoDto produto;

	public OpiniaoDto(Opiniao opiniao) {
		this.titulo = opiniao.getTitulo();
		this.descricao = opiniao.getDescricao();
		this.nota = opiniao.getNota();
		this.consumidor = new UsuarioDto(opiniao.getConsumidor());
		this.produto = new ProdutoDto(opiniao.getProduto());
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public int getNota() {
		return nota;
	}

	public UsuarioDto getConsumidor() {
		return consumidor;
	}

	public ProdutoDto getProduto() {
		return produto;
	}

}
