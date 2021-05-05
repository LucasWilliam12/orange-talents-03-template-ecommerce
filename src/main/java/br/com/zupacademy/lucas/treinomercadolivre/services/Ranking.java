package br.com.zupacademy.lucas.treinomercadolivre.services;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import br.com.zupacademy.lucas.treinomercadolivre.models.Compra;

@Service
public class Ranking implements ProcessoCompraSucesso{

	@Override
	public void processa(Compra compra) {
		Assert.isTrue(compra.processadaComSucesso(), "Essa compra não foi concluída com sucesso");
		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> request = Map.of("idCompra", compra.getId(), "idVendedor", compra.getDonoProduto().getId());
		
		restTemplate.postForEntity("http://localhost:8080/ranking", request, String.class);
		
	}

}
