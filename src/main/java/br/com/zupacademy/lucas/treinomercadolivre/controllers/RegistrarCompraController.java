package br.com.zupacademy.lucas.treinomercadolivre.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zupacademy.lucas.treinomercadolivre.config.security.UsuarioLogado;
import br.com.zupacademy.lucas.treinomercadolivre.controllers.exceptions.ObjectNotFoundException;
import br.com.zupacademy.lucas.treinomercadolivre.dto.enumns.GatewayPagamento;
import br.com.zupacademy.lucas.treinomercadolivre.models.Compra;
import br.com.zupacademy.lucas.treinomercadolivre.models.Produto;
import br.com.zupacademy.lucas.treinomercadolivre.models.Usuario;
import br.com.zupacademy.lucas.treinomercadolivre.repositories.CompraRepository;
import br.com.zupacademy.lucas.treinomercadolivre.repositories.ProdutoRepository;
import br.com.zupacademy.lucas.treinomercadolivre.repositories.UsuarioRepository;
import br.com.zupacademy.lucas.treinomercadolivre.requests.ComprarRequest;
import br.com.zupacademy.lucas.treinomercadolivre.utils.EnvioEmail;

@RestController
@RequestMapping(value = "/comprar")
public class RegistrarCompraController {

	// Não estou levando os repositories em consideração como carga intrinseca,
	// Eu acredito que criar um sql é um ponto de entendimento maior do que o próprio repository (nesse caso com implementações básicas)
	private ProdutoRepository produtoRepo;
	private UsuarioRepository usuarioRepo;
	private CompraRepository compraRepo;
	private EnvioEmail email;

	@Autowired
	public RegistrarCompraController(ProdutoRepository produtoRepo, UsuarioRepository usuarioRepo,
			CompraRepository compraRepo, EnvioEmail email) {
		this.produtoRepo = produtoRepo;
		this.usuarioRepo = usuarioRepo;
		this.compraRepo = compraRepo;
		this.email = email;
	}

	@PostMapping
	public ResponseEntity<?> efetuar(@RequestBody @Valid ComprarRequest request,
			@AuthenticationPrincipal UsuarioLogado logado, UriComponentsBuilder uriBuilder) throws BindException {

		Produto produto = produtoRepo.findById(request.getIdProduto()).get();
		Usuario comprador = usuarioRepo.findByEmail(logado.getUsername())
				.orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado com o email informado"));
		GatewayPagamento gateway = request.getGateway();

		if (produto.abate(request.getQuantidade())) {

			Compra compra = new Compra(request.getQuantidade(), produto, comprador, gateway);
			compra = compraRepo.save(compra);

			if (gateway.equals(GatewayPagamento.pagseguro)) {
				String urlPagSeguro = uriBuilder.path("/compra-pagseguro/{id}").buildAndExpand(compra.getId())
						.toString();

				email.sendEmail(produto.getDono().getEmail(), "email@system.com", "<html>O usuário: "+comprador.getEmail()+" quer comprar o produto: "+produto.getNome()+", Gateway: "+request.getGateway()+"</html>");
				return ResponseEntity.status(HttpStatus.FOUND)
						.body("pagseguro.com?" + "returnId=" + compra.getId() + "&" + "redirectUrl=" + urlPagSeguro);
			} else {
				String urlPaypal = uriBuilder.path("/compra-paypal/{id}").buildAndExpand(compra.getId())
						.toString();
				System.out.println("Email:");
				email.sendEmail(produto.getDono().getEmail(), "email@system.com", "<html>O usuário: "+comprador.getEmail()+" quer comprar o produto: "+produto.getNome()+", Gateway: "+request.getGateway()+"</html>");
				return ResponseEntity.status(HttpStatus.FOUND)
						.body("paypal.com?" + "returnId=" + compra.getId() + "&" + "redirectUrl=" + urlPaypal);
			}
		}

		BindException errorAbateEstoque = new BindException(request, "compraRequest");
		errorAbateEstoque.reject(null, "Não foi possível realizar a compra por conta da quantidade de produtos em estoque.");
		
		throw errorAbateEstoque;
	}
}
