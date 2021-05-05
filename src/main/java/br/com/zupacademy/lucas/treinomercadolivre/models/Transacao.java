package br.com.zupacademy.lucas.treinomercadolivre.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zupacademy.lucas.treinomercadolivre.dto.enumns.StatusTransacao;

@Entity
public class Transacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private StatusTransacao status;
	private @NotBlank String idTransacaoGateway;
	@ManyToOne
	private @Valid @NotNull Compra compra;
	@NotNull
	private LocalDateTime instante;

	@Deprecated
	public Transacao() {
	}
	
	public Transacao(@NotNull StatusTransacao status, @NotBlank String idTransacaoGateway, @NotNull @Valid Compra compra) {
		this.status = status;
		this.idTransacaoGateway = idTransacaoGateway;
		this.compra = compra;
		this.instante = LocalDateTime.now();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idTransacaoGateway == null) ? 0 : idTransacaoGateway.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transacao other = (Transacao) obj;
		if (idTransacaoGateway == null) {
			if (other.idTransacaoGateway != null)
				return false;
		} else if (!idTransacaoGateway.equals(other.idTransacaoGateway))
			return false;
		return true;
	}

	public boolean concluidaComSucesso() {
		return this.status.equals(StatusTransacao.SUCESSO);
	}

}
