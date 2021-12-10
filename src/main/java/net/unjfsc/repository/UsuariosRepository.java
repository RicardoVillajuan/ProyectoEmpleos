package net.unjfsc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.unjfsc.model.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {
	
	Usuario findByUsername(String username);
}
