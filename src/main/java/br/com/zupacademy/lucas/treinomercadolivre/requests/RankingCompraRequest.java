package br.com.zupacademy.lucas.treinomercadolivre.requests;

import javax.validation.constraints.NotNull;

public class RankingCompraRequest {
	
	@NotNull
	private Long idCompra;
	@NotNull
	private Long idVendedor;
	
	public RankingCompraRequest(@NotNull Long idCompra, @NotNull Long idVendedor) {
		this.idCompra = idCompra;
		this.idVendedor = idVendedor;
	}

	@Override
	public String toString() {
		return "RankingCompraRequest [idCompra=" + idCompra + ", idVendedor=" + idVendedor + "]";
	}	
	
}
