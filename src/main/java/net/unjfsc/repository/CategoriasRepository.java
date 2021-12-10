package net.unjfsc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import net.unjfsc.model.Categoria;

//Iniciamos usando la interfaces CrudRepositoy pero ahora vamor a usar una nueva interfaz que implementa el CrudRepository
//public interface CategoriasRepository extends CrudRepository<Categoria, Integer> {
public interface CategoriasRepository extends JpaRepository<Categoria, Integer> {
	
	
	
}
