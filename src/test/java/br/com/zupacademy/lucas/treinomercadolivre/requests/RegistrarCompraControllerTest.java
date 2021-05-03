package br.com.zupacademy.lucas.treinomercadolivre.requests;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BindException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zupacademy.lucas.treinomercadolivre.config.security.UsuarioLogado;
import br.com.zupacademy.lucas.treinomercadolivre.controllers.RegistrarCompraController;
import br.com.zupacademy.lucas.treinomercadolivre.dto.enumns.GatewayPagamento;
import br.com.zupacademy.lucas.treinomercadolivre.models.Categoria;
import br.com.zupacademy.lucas.treinomercadolivre.models.Compra;
import br.com.zupacademy.lucas.treinomercadolivre.models.Produto;
import br.com.zupacademy.lucas.treinomercadolivre.models.SenhaLimpa;
import br.com.zupacademy.lucas.treinomercadolivre.models.Usuario;
import br.com.zupacademy.lucas.treinomercadolivre.repositories.CompraRepository;
import br.com.zupacademy.lucas.treinomercadolivre.repositories.ProdutoRepository;
import br.com.zupacademy.lucas.treinomercadolivre.repositories.UsuarioRepository;
import br.com.zupacademy.lucas.treinomercadolivre.utils.EnvioEmail;

public class RegistrarCompraControllerTest {
	
	@Mock
	private CompraRepository compraRepo;
	@Mock
	private UsuarioRepository usuarioRepo;
	@Mock
	private ProdutoRepository produtoRepo;
	@Mock
	private EnvioEmail email;
	
	UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/");
	@InjectMocks
	private RegistrarCompraController controller = new RegistrarCompraController(produtoRepo, usuarioRepo, compraRepo, email);
	private Usuario dono;
	private Categoria categoria;
	private List<CaracteristicasRequest> caracteristicas;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		dono = new Usuario("dono@email.com", new SenhaLimpa("123456"));
		categoria = new Categoria();
		categoria.setNome("teste");
		caracteristicas = List.of(
				new CaracteristicasRequest("key", "value"),
				new CaracteristicasRequest("key2", "value2"),
				new CaracteristicasRequest("key3", "value3")
				);
	}
	
	
	@DisplayName("Deve retornar um link do gateway")
	@Test
	public void teste1() throws BindException {
		
		Mockito.when(usuarioRepo.findByEmail(Mockito.anyString())).thenReturn(Optional.of(dono));
		Mockito.doAnswer(invocation -> {
			Compra compraSendoSalva = invocation.<Compra>getArgument(0);
			ReflectionTestUtils.setField(compraSendoSalva, "id", 1L);
			return compraSendoSalva;
		}).when(compraRepo).save(Mockito.any(Compra.class));
		
		Produto produto = new Produto("teste", BigDecimal.TEN, 30, "Teste descricao", categoria, dono, caracteristicas);
		Mockito.when(produtoRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(produto));
		
		ComprarRequest request = new ComprarRequest(1L, 10, GatewayPagamento.pagseguro);
		
		ResponseEntity<?> response = controller.efetuar(request, new UsuarioLogado(dono), uriBuilder);
		
		Assertions.assertEquals("pagseguro.com?returnId=1&redirectUrl=http://localhost:8080/compra-pagseguro/1", response.getBody());
	}
	
	@DisplayName("Deve retornar uma exceção ao efetuar a compra devido ao estoque")
	@Test
	public void teste2() {
		
		Mockito.when(usuarioRepo.findByEmail(Mockito.anyString())).thenReturn(Optional.of(dono));
		Mockito.doAnswer(invocation -> {
			Compra compraSendoSalva = invocation.<Compra>getArgument(0);
			ReflectionTestUtils.setField(compraSendoSalva, "id", 1L);
			return compraSendoSalva;
		}).when(compraRepo).save(Mockito.any(Compra.class));
		
		Produto produto = new Produto("teste", BigDecimal.TEN, 30, "Teste descricao", categoria, dono, caracteristicas);
		Mockito.when(produtoRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(produto));
		
		ComprarRequest request = new ComprarRequest(1L, 40, GatewayPagamento.pagseguro);
		
		Assertions.assertThrows(BindException.class, () -> {
			controller.efetuar(request, new UsuarioLogado(dono), uriBuilder);
		});
	}
}
