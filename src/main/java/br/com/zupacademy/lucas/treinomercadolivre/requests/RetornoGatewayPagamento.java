package br.com.zupacademy.lucas.treinomercadolivre.requests;

import br.com.zupacademy.lucas.treinomercadolivre.models.Compra;
import br.com.zupacademy.lucas.treinomercadolivre.models.Transacao;

public interface RetornoGatewayPagamento {
	Transacao toTransacao(Compra compra);
}
