package br.com.zupacademy.lucas.treinomercadolivre.repositories;

import java.util.Optional;

import javax.validation.constraints.Email;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.zupacademy.lucas.treinomercadolivre.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	@Transactional(readOnly = true)
	Optional<Usuario> findByEmail(@Email String email);
	
}
