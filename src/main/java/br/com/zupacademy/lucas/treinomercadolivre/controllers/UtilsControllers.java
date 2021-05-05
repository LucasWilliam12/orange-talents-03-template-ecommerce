package br.com.zupacademy.lucas.treinomercadolivre.controllers;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.zupacademy.lucas.treinomercadolivre.requests.NovaCompraNFRequest;
import br.com.zupacademy.lucas.treinomercadolivre.requests.RankingCompraRequest;

@RestController
public class UtilsControllers {
	
	@PostMapping(value = "/notas-fiscais")
	public void criaNota(@Valid @RequestBody NovaCompraNFRequest request) throws InterruptedException {
		System.out.println("Criando nota para: "+request);
		Thread.sleep(150);
	}

	@PostMapping(value = "/ranking")
	public void ranking(@Valid @RequestBody RankingCompraRequest request) throws InterruptedException {
		System.out.println("Criando nota para: " + request);
		Thread.sleep(150);
	}

}
