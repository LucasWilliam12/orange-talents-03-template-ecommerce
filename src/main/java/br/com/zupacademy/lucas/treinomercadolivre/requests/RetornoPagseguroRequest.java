package br.com.zupacademy.lucas.treinomercadolivre.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zupacademy.lucas.treinomercadolivre.dto.enumns.StatusRetornoPagseguro;
import br.com.zupacademy.lucas.treinomercadolivre.models.Compra;
import br.com.zupacademy.lucas.treinomercadolivre.models.Transacao;

public class RetornoPagseguroRequest implements RetornoGatewayPagamento{
	
	@NotBlank
	private String idTransacao;
	@NotNull
	private StatusRetornoPagseguro status;

	public RetornoPagseguroRequest(@NotBlank String idTransacao, @NotNull StatusRetornoPagseguro status) {
		this.idTransacao = idTransacao;
		this.status = status;
	}

	@Override
	public Transacao toTransacao(Compra compra) {
		return new Transacao(status.normaliza(), idTransacao, compra);
	}

	@Override
	public String toString() {
		return "RetornoPagseguroRequest [idTransacao=" + idTransacao + ", status=" + status + "]";
	}
	
}
