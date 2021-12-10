package net.unjfsc.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.unjfsc.model.Categoria;

public interface ICategoriasService {

	void guardar(Categoria categoria);
	List<Categoria> buscarTodas();
	Categoria buscarPorId(Integer idCategoria);
	void eliminar(int idCategoria);
	
	Page<Categoria> buscarTodas(Pageable page);
}
