package br.com.zupacademy.lucas.treinomercadolivre.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zupacademy.lucas.treinomercadolivre.controllers.exceptions.ObjectNotFoundException;
import br.com.zupacademy.lucas.treinomercadolivre.models.Compra;
import br.com.zupacademy.lucas.treinomercadolivre.repositories.CompraRepository;
import br.com.zupacademy.lucas.treinomercadolivre.requests.RetornoGatewayPagamento;
import br.com.zupacademy.lucas.treinomercadolivre.requests.RetornoPagseguroRequest;
import br.com.zupacademy.lucas.treinomercadolivre.requests.RetornoPaypalRequest;
import br.com.zupacademy.lucas.treinomercadolivre.services.ProcessosCompra;

@RestController
public class ConfirmarCompraController {

	@Autowired
	private CompraRepository compraRepo;
	@Autowired
	private ProcessosCompra processos;

	@PostMapping(value = "/retorno-pagseguro/{id}")
	public ResponseEntity<String> efetuarPagseguro(@PathVariable("id") Long idCompra,
			@Valid RetornoPagseguroRequest request, UriComponentsBuilder uriBuilder) {
		String link = uriBuilder.path("/retorno-pagseguro/"+idCompra).build().toString();
		return processa(idCompra, request, link);
	}

	@PostMapping(value = "/retorno-paypal/{id}")
	public ResponseEntity<String> efetuarPaypal(@PathVariable("id") Long idCompra,
			@Valid RetornoPaypalRequest request, UriComponentsBuilder uriBuilder) {
		String link = uriBuilder.path("/retorno-paypal/"+idCompra).build().toString();
		return processa(idCompra, request, link);
	}

	private ResponseEntity<String> processa(Long idCompra, RetornoGatewayPagamento request, String link) {
		Compra compra = compraRepo.findById(idCompra).orElseThrow(
				() -> new ObjectNotFoundException("A compra não foi encontrada com o id informado: " + idCompra));
		compra.adicionaTransacao(request);
		compraRepo.save(compra);

		processos.processa(compra, link);

		return ResponseEntity.ok(request.toString());
	}
}
