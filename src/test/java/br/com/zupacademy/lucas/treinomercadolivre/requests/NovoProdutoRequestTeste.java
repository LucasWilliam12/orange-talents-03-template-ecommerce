package br.com.zupacademy.lucas.treinomercadolivre.requests;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class NovoProdutoRequestTeste {
	@DisplayName("Deveria lidar com caracteristicas duplicadas")
	@ParameterizedTest
	@MethodSource("gerador")
	void teste1(Boolean esperado, List<CaracteristicasRequest> novasCaracteristicas) {
		NovoProdutoRequest request = new NovoProdutoRequest("Produto teste",
				BigDecimal.TEN, 10, "descrição teste", 1L, novasCaracteristicas);
		
		Assertions.assertEquals(esperado, request.temCaracteristicasIguais());
	}

	public static Stream<Arguments> gerador(){
		return Stream.of(
				Arguments.of(false, List.of()),
				Arguments.of(false, List.of(new CaracteristicasRequest("key", "value"))),
				Arguments.of(false, List.of(new CaracteristicasRequest("key", "value"), new CaracteristicasRequest("key1", "value"))),
				Arguments.of(true, List.of(new CaracteristicasRequest("key", "value"), 
						new CaracteristicasRequest("key", "value")))
				);
	}
}
