package net.unjfsc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.unjfsc.model.Categoria;
import net.unjfsc.service.ICategoriasService;

@Controller
@RequestMapping(value="/categorias")
public class CategoriasController {
	
	@Autowired
	//@Qualifier("categoriasServiceJPA")  no recomendable , mejor usamos @primary
	private ICategoriasService serviceCategorias;
	
	// @GetMapping("/index")
	/*@RequestMapping(value="/index", method=RequestMethod.GET)
	public String mostrarIndex(Model model) { 
		
		List<Categoria> lista=serviceCategorias.buscarTodas();
		model.addAttribute("categorias",lista);
		return "categorias/listCategorias";
	}*/
	
	@GetMapping(value = "/indexPaginate")
	public String mostrarIndexPaginado(Model model,Pageable page) {
		Page<Categoria> lista=serviceCategorias.buscarTodas(page);
		model.addAttribute("categorias",lista);
		
		return "categorias/listCategorias";
	}
	
	// @GetMapping("/create")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String crear(Categoria categoria){
		
		return "categorias/formCategoria";
	}
	
	// @PostMapping("/save")
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String guardar(Categoria categoria,BindingResult result,RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			
			for(ObjectError error: result.getAllErrors()) {
				System.out.println("Ocurrio un error: "+error.getDefaultMessage());
			}
			return "categorias/formCategoria";
		}
		
		serviceCategorias.guardar(categoria);
		//addFlashAttribute se usa para que nuestro valos enviado ala web se pueda pasar a 
		//traves de una redireccion, sino usariamos AddAttribute
		attributes.addFlashAttribute("msg","Registro Guardado");
		
		return "redirect:/categorias/index";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int idcategoria,Model model) {
		Categoria categoria=serviceCategorias.buscarPorId(idcategoria);
		model.addAttribute("categoria",categoria);
		
		return "categorias/formCategoria";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idcategoria,RedirectAttributes attributes) {
		serviceCategorias.eliminar(idcategoria);
		attributes.addFlashAttribute("msg","Registro Eliminado");
		
		return "redirect:/categorias/index";
	}

}
