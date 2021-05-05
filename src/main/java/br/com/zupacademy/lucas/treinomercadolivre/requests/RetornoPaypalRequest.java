package br.com.zupacademy.lucas.treinomercadolivre.requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import br.com.zupacademy.lucas.treinomercadolivre.dto.enumns.StatusTransacao;
import br.com.zupacademy.lucas.treinomercadolivre.models.Compra;
import br.com.zupacademy.lucas.treinomercadolivre.models.Transacao;

public class RetornoPaypalRequest implements RetornoGatewayPagamento{
	
	@Min(0)
	@Max(1)
	private int status;
	@NotBlank
	private String idTransacao;
	
	public RetornoPaypalRequest(@Min(0) @Max(1) int status, @NotBlank String idTransacao) {
		super();
		this.status = status;
		this.idTransacao = idTransacao;
	}
	
	@Override
	public Transacao toTransacao(Compra compra) {
		return new Transacao(this.status == 0 ? StatusTransacao.ERRO : StatusTransacao.SUCESSO,
				idTransacao, compra);
	}
}
