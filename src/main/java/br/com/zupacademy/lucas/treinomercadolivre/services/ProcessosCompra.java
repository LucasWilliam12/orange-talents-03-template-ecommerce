package br.com.zupacademy.lucas.treinomercadolivre.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zupacademy.lucas.treinomercadolivre.models.Compra;
import br.com.zupacademy.lucas.treinomercadolivre.utils.EnvioEmail;

@Service
public class ProcessosCompra {
	
	@Autowired
	private Set<ProcessoCompraSucesso> processos;
	@Autowired
	private EnvioEmail envio;

	public void processa(Compra compra, String link) {
		
		if(compra.processadaComSucesso()) {
			processos.forEach(processo -> processo.processa(compra));
			envio.sendEmail(compra.getComprador().getEmail(), "system@email.com.br", "<html>Sua compra foi realizada com sucesso.</html>");
		}else {
			envio.sendEmail(compra.getComprador().getEmail(), "system@email.com.br", "<html>Sua compra não foi realizada. Tente novamente: "+link+"</html>");
		}
		
	}

}
