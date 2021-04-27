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
import br.com.zupacademy.lucas.treinomercadolivre.dto.PerguntaDto;
import br.com.zupacademy.lucas.treinomercadolivre.models.Pergunta;
import br.com.zupacademy.lucas.treinomercadolivre.models.Produto;
import br.com.zupacademy.lucas.treinomercadolivre.models.Usuario;
import br.com.zupacademy.lucas.treinomercadolivre.repositories.PerguntaRepository;
import br.com.zupacademy.lucas.treinomercadolivre.repositories.ProdutoRepository;
import br.com.zupacademy.lucas.treinomercadolivre.repositories.UsuarioRepository;
import br.com.zupacademy.lucas.treinomercadolivre.requests.NovaPerguntaRequest;
import br.com.zupacademy.lucas.treinomercadolivre.utils.EnvioEmail;

@RestController
@RequestMapping(value = "/produtos")
public class CadastrarPerguntaController {
	
	@Autowired
	private ProdutoRepository produtoRepo;
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@Autowired
	private PerguntaRepository perguntaRepo;
	
	@Autowired
	private EnvioEmail envioEmail;
	
	@PostMapping(value = "/{id}/pergunta")
	public ResponseEntity<PerguntaDto> cadastrar(@RequestBody @Valid NovaPerguntaRequest request,
			@PathVariable("id") Long id, @AuthenticationPrincipal UsuarioLogado logado){
		Produto produto = produtoRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException("O produto não foi encontrado com id informado: "+id));
		Usuario consumidor = usuarioRepo.findByEmail(logado.getUsername()).orElseThrow(() -> new ObjectNotFoundException("O usuario informado não foi encontrado"));
		
		if(produto.pertenceAoUsuario(consumidor)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
		
		Pergunta pergunta = perguntaRepo.save(request.toModel(produto, consumidor));
		envioEmail.sendEmail(consumidor.getEmail(), "email@system.com", "<html>"+pergunta.getTitulo()+"</html>");
		return ResponseEntity.ok(new PerguntaDto(pergunta));
	}
	
}
