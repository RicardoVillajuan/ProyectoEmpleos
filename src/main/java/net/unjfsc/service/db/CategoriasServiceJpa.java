package net.unjfsc.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.unjfsc.model.Categoria;
import net.unjfsc.repository.CategoriasRepository;
import net.unjfsc.service.ICategoriasService;

@Service
//@Primary  para hacer la injection @Autowired se tiene que tener solo una clase que imp
//implemente la iterfaz ICategorias service si fuera 2 los que lo implementan saldra un error 
//ya que se confunde y para eso usamos @Primary para quedicha clase tenga mas  provilegi
@Primary
public class CategoriasServiceJpa implements ICategoriasService {
	
	@Autowired
	private CategoriasRepository categoriasRepo;
	
	@Override
	public void guardar(Categoria categoria) {
		categoriasRepo.save(categoria);
	}

	@Override
	public List<Categoria> buscarTodas() {
		// TODO Auto-generated method stub
		
		return categoriasRepo.findAll();
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria) {
		// TODO Auto-generated method stub
		Optional<Categoria> optional= categoriasRepo.findById(idCategoria);
		if(optional.isPresent()) {
			return optional.get();
		}
		
		return null;
	}
	
	@Override
	public void eliminar(int idCategoria) {
		// TODO Auto-generated method stub
		categoriasRepo.deleteById(idCategoria);
	}

	@Override
	public Page<Categoria> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return categoriasRepo.findAll(page);
	}

}
