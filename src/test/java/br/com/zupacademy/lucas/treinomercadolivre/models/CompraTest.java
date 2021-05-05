package br.com.zupacademy.lucas.treinomercadolivre.models;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import br.com.zupacademy.lucas.treinomercadolivre.dto.enumns.GatewayPagamento;
import br.com.zupacademy.lucas.treinomercadolivre.dto.enumns.StatusTransacao;
import br.com.zupacademy.lucas.treinomercadolivre.requests.CaracteristicasRequest;
import br.com.zupacademy.lucas.treinomercadolivre.requests.RetornoGatewayPagamento;

public class CompraTest {
	
	@Test
	@DisplayName("Deveria adicionar uma transacao")
	public void teste1() {
		Compra novaCompra = novaCompra();
		RetornoGatewayPagamento retorno = (compra) -> {
			return new Transacao(StatusTransacao.SUCESSO, "1", compra);
		};
		
		novaCompra.adicionaTransacao(retorno);
	}
	

	@Test
	@DisplayName("Não pode aceitar uma transacao igual")
	public void teste2() {
		Compra novaCompra = novaCompra();
		RetornoGatewayPagamento retorno = (compra) -> {
			return new Transacao(StatusTransacao.ERRO, "1", compra);
		};
		
		novaCompra.adicionaTransacao(retorno);
		
		RetornoGatewayPagamento retorno2 = (compra) -> {
			return new Transacao(StatusTransacao.ERRO, "1", compra);
		};
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			novaCompra.adicionaTransacao(retorno2);
		});
	}
	
	@Test
	@DisplayName("Não pode aceitar uma transacao caso já tenha uma outra concluida")
	public void teste3() {
		Compra novaCompra = novaCompra();
		RetornoGatewayPagamento retorno = (compra) -> {
			return new Transacao(StatusTransacao.SUCESSO, "1", compra);
		};
		
		novaCompra.adicionaTransacao(retorno);
		
		RetornoGatewayPagamento retorno2 = (compra) -> {
			return new Transacao(StatusTransacao.SUCESSO, "2", compra);
		};
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			novaCompra.adicionaTransacao(retorno2);
		});
	}
	
	
	@DisplayName("Deveria verificar se uma compra foi concluída com sucesso")
	@ParameterizedTest
	@MethodSource("geradorTeste4")
	public void teste4(boolean resultadoEsperado, Collection<RetornoGatewayPagamento> retornos) {
		Compra novaCompra = novaCompra();
		retornos.forEach(retorno -> novaCompra.adicionaTransacao(retorno));
		
		Assertions.assertEquals(resultadoEsperado, novaCompra.processadaComSucesso());
	}
	
	public static Stream<Arguments> geradorTeste4(){
		RetornoGatewayPagamento retornoSucesso1 = (compra) -> {
			return new Transacao(StatusTransacao.SUCESSO, "1", compra);
		};
		
		RetornoGatewayPagamento retornoErro2 = (compra) -> {
			return new Transacao(StatusTransacao.ERRO, "1", compra);
		};
		
		return Stream.of(
				Arguments.of(true, List.of(retornoSucesso1)),
				Arguments.of(false, List.of(retornoErro2)),
				Arguments.of(false, List.of())
			);
	}
	
	public Compra novaCompra() {
		Set<CaracteristicasRequest> caracteristicas = Set.of(
					new CaracteristicasRequest("kay", "value"),
					new CaracteristicasRequest("kay2", "value2"),
					new CaracteristicasRequest("kay3", "value3")
				);
		Usuario dono = new Usuario("email@email.com", new SenhaLimpa("123456"));
		Categoria categoria = new Categoria();
		categoria.setNome("teste");
		Produto produto = new Produto("teste", BigDecimal.TEN, 30, "teste", categoria, dono, caracteristicas);
		
		return new Compra(10, produto, dono, GatewayPagamento.pagseguro);
	}
}
