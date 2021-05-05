package br.com.zupacademy.lucas.treinomercadolivre.dto.enumns;

public enum StatusRetornoPagseguro {
	SUCESSO, ERRO;

	public StatusTransacao normaliza() {
		if(this.equals(SUCESSO)) {
			return StatusTransacao.SUCESSO;
		}
		
		return StatusTransacao.ERRO;
	}
}
