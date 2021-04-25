package br.com.zupacademy.lucas.treinomercadolivre.requests;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.zupacademy.lucas.treinomercadolivre.models.Categoria;
import br.com.zupacademy.lucas.treinomercadolivre.repositories.CategoriaRepository;

public class NovaCategoriaRequestTeste {
	
	@Mock
	private CategoriaRepository repo;
	
	@BeforeEach
	@SuppressWarnings("deprecation")
	public void beforeEach() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	@DisplayName("deveria cadastrar uma categoria com uma categoria mãe intrelaçada")
	public void deveriaCriarUmaCategoriaComUmaCategoriaMae() {
		
		NovaCategoriaRequest request = new NovaCategoriaRequest("Teste", 1L);
		Categoria categoriaMae = new Categoria();
		categoriaMae.setNome("categoriaMae");
		
		Mockito.when(repo.findById(1L)).thenReturn(Optional.of(categoriaMae));
		request.toModel(repo);
		
		Mockito.verify(repo).findById(1L);
	}
	
	@Test
	@DisplayName("não deveria cadastrar uma categoria com uma categoria mãe intrelaçada")
	public void naoDeveriaCriarUmaCategoriaComUmaCategoriaMae() {
		
		NovaCategoriaRequest request = new NovaCategoriaRequest("Teste", null);

		Mockito.when(repo.findById(1L)).thenReturn(Optional.empty());
		request.toModel(repo);
		
		Mockito.verifyNoInteractions(repo);
	}
}
