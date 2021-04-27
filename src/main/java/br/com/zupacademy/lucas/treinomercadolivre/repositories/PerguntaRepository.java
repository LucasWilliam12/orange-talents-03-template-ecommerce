package br.com.zupacademy.lucas.treinomercadolivre.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zupacademy.lucas.treinomercadolivre.models.Pergunta;

public interface PerguntaRepository extends JpaRepository<Pergunta, Long>{

}
