package br.com.zupacademy.lucas.treinomercadolivre.requests;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class NovasImagensRequest {
	
	@Size(min = 1)
	@NotNull
	private List<MultipartFile> imagens = new ArrayList<>();

	public NovasImagensRequest(@Size(min = 1) @NotNull List<MultipartFile> imagens) {
		this.imagens = imagens;
	}
	
	public void setImagens(List<MultipartFile> imagens) {
		this.imagens = imagens;
	}
	
	public List<MultipartFile> getImagens() {
		return imagens;
	}
}
