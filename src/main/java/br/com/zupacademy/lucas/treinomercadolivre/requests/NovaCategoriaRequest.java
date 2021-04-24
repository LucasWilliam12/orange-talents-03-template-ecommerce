package br.com.zupacademy.lucas.treinomercadolivre.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.zupacademy.lucas.treinomercadolivre.controllers.exceptions.ObjectNotFoundException;
import br.com.zupacademy.lucas.treinomercadolivre.controllers.validators.UniqueData;
import br.com.zupacademy.lucas.treinomercadolivre.models.Categoria;
import br.com.zupacademy.lucas.treinomercadolivre.repositories.CategoriaRepository;

public class NovaCategoriaRequest {
	
	// Atributos
	@NotBlank @NotNull
	@UniqueData(domainClass = Categoria.class, fieldName = "nome")
	private String nome;
	@Positive
	private Long idCategoriaMae;

	// Construtores
	@Deprecated
	public NovaCategoriaRequest() {
	}

	public NovaCategoriaRequest(@NotBlank @NotNull @UniqueData(domainClass = Categoria.class, fieldName = "nome") String nome,
			@Positive Long idCategoriaMae) {
		this.nome = nome;
		this.idCategoriaMae = idCategoriaMae;
	}
	
	public Categoria toModel(CategoriaRepository repo) {
		
		Categoria cat = new Categoria();
		cat.setNome(nome);
		
		if(idCategoriaMae != null) {
			Categoria catMae = repo.findById(idCategoriaMae).orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada com o id informado", "idCategoriaMae"));
			cat.setCategoriaMae(catMae);
		}
		
		return cat;
	}

	public String getNome() {
		return nome;
	}

	public Long getIdCategoriaMae() {
		return idCategoriaMae;
	}
	
}
