package net.unjfsc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.unjfsc.model.Vacante;

public interface VacantesRepository extends JpaRepository<Vacante, Integer>{
	
	List<Vacante> findByEstatus(String estatus);
	
	List<Vacante> findByDestacadoAndEstatusOrderByIdDesc(int destacado,String estatus);

	List<Vacante> findBySalarioBetweenOrderBySalarioAsc(double s1, double s2);

	List<Vacante> findByEstatusIn(String[] estatus);
}
