package br.com.zupacademy.lucas.treinomercadolivre.dto;

import br.com.zupacademy.lucas.treinomercadolivre.models.Categoria;

public class NovaCategoriaDto {
	
	// Atributos
	private String nome;
	private NovaCategoriaDto categoriaMae;
	
	// Construtores
	public NovaCategoriaDto() {
		
	}
	
	public NovaCategoriaDto(Categoria cat) {
		this.nome = cat.getNome();
		this.categoriaMae = cat.getCategoriaMae() != null ? new NovaCategoriaDto(cat.getCategoriaMae()) : null;
	}

	// Getters
	public String getNome() {
		return nome;
	}

	public NovaCategoriaDto getCategoriaMae() {
		return categoriaMae;
	}
	
}
