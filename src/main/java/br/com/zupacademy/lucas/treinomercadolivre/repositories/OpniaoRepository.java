package br.com.zupacademy.lucas.treinomercadolivre.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zupacademy.lucas.treinomercadolivre.models.Opiniao;

public interface OpniaoRepository extends JpaRepository<Opiniao, Long> {
	
}
