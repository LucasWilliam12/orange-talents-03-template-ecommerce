package br.com.zupacademy.lucas.treinomercadolivre.models;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import br.com.zupacademy.lucas.treinomercadolivre.requests.CaracteristicasRequest;

public class ProdutoTeste {

	@DisplayName("Um produto precisa ter no mínimo 3 caracteristicas")
	@ParameterizedTest
	@MethodSource("geradorTeste1")
	public void teste1(Collection<CaracteristicasRequest> caracteristicas) {

		Categoria categoria = new Categoria();
		categoria.setNome("categoria teste");

		Usuario dono = new Usuario("email@email.com.br", new SenhaLimpa("senhaa"));

		new Produto("produto teste", BigDecimal.TEN, 10, "descrição teste", categoria, dono, caracteristicas);
	}

	public static Stream<Arguments> geradorTeste1() {
		return Stream.of(
				Arguments.of(List.of(new CaracteristicasRequest("key", "value"),
						new CaracteristicasRequest("key2", "value2"), new CaracteristicasRequest("key3", "value3"))),
				Arguments.of(List.of(new CaracteristicasRequest("key", "value"),
						new CaracteristicasRequest("key2", "value2"), new CaracteristicasRequest("key3", "value3"),
						new CaracteristicasRequest("key4", "value4"))));
	}

	@DisplayName("Um produto não pode ser criado com menos de 3 caracteristicas")
	@ParameterizedTest
	@MethodSource("geradorTeste2")
	public void teste2(Collection<CaracteristicasRequest> caracteristicas) {

		Categoria categoria = new Categoria();
		categoria.setNome("categoria teste");

		Usuario dono = new Usuario("email@email.com.br", new SenhaLimpa("senhaa"));

		Assertions.assertThrows(IllegalArgumentException.class, 
				() -> new Produto("produto teste", BigDecimal.TEN, 10,
				"descrição teste", categoria, dono, caracteristicas));
	}
	
	public static Stream<Arguments> geradorTeste2(){
		return Stream.of(
				Arguments.of(List.of(
						new CaracteristicasRequest("key", "value"),
						new CaracteristicasRequest("key2", "value2"))),
				Arguments.of(List.of(
						new CaracteristicasRequest("key", "value")))
				);
	}
}
