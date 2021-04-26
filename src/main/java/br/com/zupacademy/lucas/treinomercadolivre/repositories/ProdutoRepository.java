package br.com.zupacademy.lucas.treinomercadolivre.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.zupacademy.lucas.treinomercadolivre.models.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
