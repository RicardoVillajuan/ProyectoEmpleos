package net.unjfsc.service;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.unjfsc.model.Vacante;

//PARA EL EJEMPLO SIN BD
@Service
public class VacantesServiceImpl implements IVacantesServices{
	
	private List<Vacante> lista=null;
	
	public VacantesServiceImpl() {
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		lista=new LinkedList<Vacante>();
		try {
			
			//Creamos la oferta de Trabajo 1.
			Vacante vacante1=new Vacante();
			vacante1.setId(1);
			vacante1.setNombre("Ingeniero civil");
			vacante1.setDescripcion("Solicitamos Ing. Civil para diseñar puente peatonal. ");
			vacante1.setFecha(sdf.parse("08-02-2019"));
			vacante1.setSalario(500.0);
			vacante1.setDestacado(1);
			vacante1.setImagen("empresa1.png");
			
			//Creamos la oferta de Trabajo 2.
			Vacante vacante2=new Vacante();
			vacante2.setId(2);
			vacante2.setNombre("Contador Publico");
			vacante2.setDescripcion("Empresa importate solicita contador con 5 años de experiencia titulado. ");
			vacante2.setFecha(sdf.parse("09-02-2019"));
			vacante2.setSalario(12000.0);
			vacante2.setDestacado(0);
			vacante2.setImagen("empresa2.png");
			
			//Creamos la oferta de Trabajo 1.
			Vacante vacante3=new Vacante();
			vacante3.setId(3);
			vacante3.setNombre("Ingeniero Electronico");
			vacante3.setDescripcion("Empresa internacional solicita contador con 5 años de experiencia titulado");
			vacante3.setFecha(sdf.parse("10-02-2019"));
			vacante3.setSalario(4500.0);
			vacante3.setDestacado(0);
			//vacante3.setImagen("empresa1.png");
			
			//Creamos la oferta de Trabajo 1.
			Vacante vacante4=new Vacante();
			vacante4.setId(4);
			vacante4.setNombre("Diseñador Grafico");
			vacante4.setDescripcion("Solicitamos Diseñador Grafico para diseñar puente peatonal. ");
			vacante4.setFecha(sdf.parse("11-02-2019"));
			vacante4.setSalario(4500.0);
			vacante4.setDestacado(1);
			vacante4.setImagen("empresa4.png");
			
			//Agregamos los 4 objetos para agregarle a la lista
			
			lista.add(vacante1);
			lista.add(vacante2);
			lista.add(vacante3);
			lista.add(vacante4);
			
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error "+e.getMessage());
		}
		
	}
	
	@Override
	public List<Vacante> buscarTodas() {
		// TODO Auto-generated method stub
		return lista;
	}

	@Override
	public Vacante buscarPorId(Integer idVacante) {
		// TODO Auto-generated method stub
		
		for(Vacante v: lista) {
			if(v.getId()==idVacante) {
				return v;
			}
		}
		
		return null;
	}

	@Override
	public void guardar(Vacante vacante) {
		// TODO Auto-generated method stub
		lista.add(vacante);
	}

	@Override
	public List<Vacante> buscarDestacadas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(Integer idVacante) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Vacante> buscarByExample(Example<Vacante> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Vacante> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
