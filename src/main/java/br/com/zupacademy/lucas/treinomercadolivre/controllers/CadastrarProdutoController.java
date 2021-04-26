package br.com.zupacademy.lucas.treinomercadolivre.controllers;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
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
import br.com.zupacademy.lucas.treinomercadolivre.requests.NovasImagensRequest;
import br.com.zupacademy.lucas.treinomercadolivre.requests.NovoProdutoRequest;
import br.com.zupacademy.lucas.treinomercadolivre.utils.UploaderFake;

@RestController
@RequestMapping(value = "/produtos")
public class CadastrarProdutoController {
	
	@Autowired
	private CategoriaRepository categoriaRepo;
	
	@Autowired
	private ProdutoRepository produtoRepo;
	
	@Autowired
	private UsuarioRepository usuarioRepo;

	@Autowired
	private UploaderFake uploaderFake;
	
	@InitBinder(value = "novoProdutoRequest")
	public void InitBinder(WebDataBinder webDataBinder){
		webDataBinder.addValidators(new ProibeCaracteristicaComNomeDuplicadoValidator());
	}
	
	@PostMapping
	public ResponseEntity<ProdutoDto> cadastrar(@Valid @RequestBody NovoProdutoRequest request,
			@AuthenticationPrincipal UsuarioLogado usuario, UriComponentsBuilder uriBuilder ){
		Usuario dono = usuarioRepo.findByEmail(usuario.getUsername()).orElseThrow(() -> new ObjectNotFoundException("Usuario com o email informado não encontrado: "+usuario.getUsername()));
		Produto produto = request.toModel(categoriaRepo,dono);
		
		produto = produtoRepo.save(produto);
		
		return ResponseEntity.created(uriBuilder.path("/produtos/{id}").build(produto.getId()))
				.body(new ProdutoDto(produto));
	}
	
	@PostMapping(value = "/{id}/imagens")
	public ResponseEntity<ProdutoDto> adicionaImagens(@PathVariable("id") Long id,@Valid NovasImagensRequest request, @AuthenticationPrincipal UsuarioLogado usuario) {
		Usuario dono = usuarioRepo.findByEmail(usuario.getUsername()).orElseThrow(() -> new ObjectNotFoundException("Usuario com o email informado não encontrado: "+usuario.getUsername()));
		Set<String> links = uploaderFake.envia(request.getImagens());
		Produto produto = produtoRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException("Produto com o id informado não encontrado: "+id));
		
		if(!produto.pertenceAoUsuario(dono)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
		
		produto.associaImagens(links);
		
		produto = produtoRepo.save(produto);
		
		return ResponseEntity.ok(new ProdutoDto(produto));
	}
	
}
