package br.com.zupacademy.lucas.treinomercadolivre.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zupacademy.lucas.treinomercadolivre.config.security.UsuarioLogado;
import br.com.zupacademy.lucas.treinomercadolivre.controllers.exceptions.ObjectNotFoundException;
import br.com.zupacademy.lucas.treinomercadolivre.dto.OpiniaoDto;
import br.com.zupacademy.lucas.treinomercadolivre.models.Opiniao;
import br.com.zupacademy.lucas.treinomercadolivre.models.Produto;
import br.com.zupacademy.lucas.treinomercadolivre.models.Usuario;
import br.com.zupacademy.lucas.treinomercadolivre.repositories.OpniaoRepository;
import br.com.zupacademy.lucas.treinomercadolivre.repositories.ProdutoRepository;
import br.com.zupacademy.lucas.treinomercadolivre.repositories.UsuarioRepository;
import br.com.zupacademy.lucas.treinomercadolivre.requests.NovaOpiniaoRequest;

@RestController
@RequestMapping(value = "/produtos")
public class CadastrarOpiniaoController {

	@Autowired
	private ProdutoRepository produtoRepo;
	@Autowired
	private UsuarioRepository usuarioRepo;
	@Autowired
	private OpniaoRepository opniaoRepo;
	
	@PostMapping(value = "/{id}/opnioes")
	public ResponseEntity<OpiniaoDto> cadastrar(@RequestBody @Valid NovaOpiniaoRequest request, 
			@PathVariable("id") Long id, @AuthenticationPrincipal UsuarioLogado logado) {
		Produto produto = produtoRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException("O produto não foi encontrado com id informado: "+id));
		Usuario usuario = usuarioRepo.findByEmail(logado.getUsername()).orElseThrow(() -> new ObjectNotFoundException("O usuario informado não foi encontrado"));
		
		if(produto.pertenceAoUsuario(usuario)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
		
		Opiniao opniao = request.toModel(usuario, produto);
		usuario.addOpniao(opniao);
		produto.addOpniao(opniao);
		
		opniao = opniaoRepo.save(opniao);
		usuarioRepo.save(usuario);
		produtoRepo.save(produto);
		
		return ResponseEntity.ok(new OpiniaoDto(opniao));
	}
	
}
