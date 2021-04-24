package br.com.zupacademy.lucas.treinomercadolivre.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zupacademy.lucas.treinomercadolivre.dto.NovaCategoriaDto;
import br.com.zupacademy.lucas.treinomercadolivre.models.Categoria;
import br.com.zupacademy.lucas.treinomercadolivre.repositories.CategoriaRepository;
import br.com.zupacademy.lucas.treinomercadolivre.requests.NovaCategoriaRequest;

@RestController
@RequestMapping(value = "/categorias")
public class CadastrarCategoriaController {
	
	@Autowired
	private CategoriaRepository repo;
	
	@PostMapping
	public ResponseEntity<NovaCategoriaDto> cadastrar(@Valid @RequestBody NovaCategoriaRequest request, UriComponentsBuilder uriBuilder){
		
		Categoria cat = request.toModel(repo);
		
		cat = repo.save(cat);
		
		return ResponseEntity.created(uriBuilder.path("/categorias/{id}").build(cat.getId()))
				.body(new NovaCategoriaDto(cat));
	}
	
}
