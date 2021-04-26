package br.com.zupacademy.lucas.treinomercadolivre.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zupacademy.lucas.treinomercadolivre.config.security.UsuarioLogado;
import br.com.zupacademy.lucas.treinomercadolivre.controllers.exceptions.ObjectNotFoundException;
import br.com.zupacademy.lucas.treinomercadolivre.controllers.validators.ProibeCaracteristicaComNomeDuplicadoValidator;
import br.com.zupacademy.lucas.treinomercadolivre.dto.ProdutoDto;
import br.com.zupacademy.lucas.treinomercadolivre.models.Produto;
import br.com.zupacademy.lucas.treinomercadolivre.models.Usuario;
import br.com.zupacademy.lucas.treinomercadolivre.repositories.CategoriaRepository;
import br.com.zupacademy.lucas.treinomercadolivre.repositories.ProdutoRepository;
import br.com.zupacademy.lucas.treinomercadolivre.repositories.UsuarioRepository;
import br.com.zupacademy.lucas.treinomercadolivre.requests.NovoProdutoRequest;

@RestController
@RequestMapping(value = "/produtos")
public class CadastrarProdutoController {
	
	@Autowired
	private CategoriaRepository categoriaRepo;
	
	@Autowired
	private ProdutoRepository produtoRepo;
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@InitBinder
	public void InitBinder(WebDataBinder webDataBinder){
		webDataBinder.addValidators(new ProibeCaracteristicaComNomeDuplicadoValidator());
	}
	
	@PostMapping
	public ResponseEntity<ProdutoDto> cadastrar(@Valid @RequestBody NovoProdutoRequest request,
			@AuthenticationPrincipal UsuarioLogado usuario, UriComponentsBuilder uriBuilder ){
		Usuario dono = usuarioRepo.findByEmail(usuario.getUsername())
				.orElseThrow(() -> new ObjectNotFoundException("Usuario com o email informado não encontrado: "+usuario.getUsername()));
		Produto produto = request.toModel(categoriaRepo,dono);
		produto = produtoRepo.save(produto);
		
		return ResponseEntity.created(uriBuilder.path("/produtos/{id}").build(produto.getId()))
				.body(new ProdutoDto(produto));
	}
	
}
