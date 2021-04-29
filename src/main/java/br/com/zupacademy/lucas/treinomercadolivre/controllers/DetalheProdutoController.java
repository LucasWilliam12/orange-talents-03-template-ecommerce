package br.com.zupacademy.lucas.treinomercadolivre.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zupacademy.lucas.treinomercadolivre.controllers.exceptions.ObjectNotFoundException;
import br.com.zupacademy.lucas.treinomercadolivre.dto.DetalheProdutoDto;
import br.com.zupacademy.lucas.treinomercadolivre.models.Produto;
import br.com.zupacademy.lucas.treinomercadolivre.repositories.ProdutoRepository;

@RestController
@RequestMapping(value = "/produtos")
public class DetalheProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepo;
	
	@GetMapping(value = "/{id}/detalhes")
	public ResponseEntity<DetalheProdutoDto> detalhar(@PathVariable("id") Long id) {
		Produto produto = produtoRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException("O produto não foi encontrado com o id informado: "+id));
		DetalheProdutoDto detalhe = new DetalheProdutoDto(produto);
		
		return ResponseEntity.ok(detalhe);
	}

}
