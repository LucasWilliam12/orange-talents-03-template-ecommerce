package br.com.zupacademy.lucas.treinomercadolivre.models;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
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
	
	@DisplayName("O abate só pode ser realizado se o valor for maior que zero")
	@ParameterizedTest
	@CsvSource({"0", "-1", "-100"})
	public void testeAbate(int qtd) {
		List<CaracteristicasRequest> caracteristicas = List.of(
					new CaracteristicasRequest("key", "value"),
					new CaracteristicasRequest("key2", "value2"),
					new CaracteristicasRequest("key3", "value3")
				);
		
		Categoria cat = new Categoria();
		cat.setNome("teste categoria");
		Usuario dono = new Usuario("email@email.com.br", new SenhaLimpa("senhaaa"));
		
		Produto produto = new Produto("teste", BigDecimal.TEN, qtd, "teste produto",
				cat, dono, caracteristicas);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> produto.abate(qtd));
	}
	
	@DisplayName("Verificando se foi possível realizar o abate")
	@ParameterizedTest
	@CsvSource({"1,1,true", "1,2,false", "4,2,true", "1,5,false"})
	public void testeAbate2(int estoque, int qtdPedida, boolean resultadoEsperado) {
		List<CaracteristicasRequest> caracteristicas = List.of(
					new CaracteristicasRequest("key", "value"),
					new CaracteristicasRequest("key2", "value2"),
					new CaracteristicasRequest("key3", "value3")
				);
		
		Categoria cat = new Categoria();
		cat.setNome("teste categoria");
		Usuario dono = new Usuario("email@email.com.br", new SenhaLimpa("senhaaa"));
		
		Produto produto = new Produto("teste", BigDecimal.TEN, estoque, "teste produto",
				cat, dono, caracteristicas);
		
		boolean resultado = produto.abate(qtdPedida);
		Assertions.assertEquals(resultadoEsperado, resultado);
	}
	
	public static Stream<Arguments> geradorTeste1() {
		return Stream.of(
				Arguments.of(List.of(new CaracteristicasRequest("key", "value"),
						new CaracteristicasRequest("key2", "value2"), new CaracteristicasRequest("key3", "value3"))),
				Arguments.of(List.of(new CaracteristicasRequest("key", "value"),
						new CaracteristicasRequest("key2", "value2"), new CaracteristicasRequest("key3", "value3"),
						new CaracteristicasRequest("key4", "value4"))));
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
