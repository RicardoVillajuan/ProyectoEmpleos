package net.unjfsc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.unjfsc.model.Perfil;
import net.unjfsc.model.Usuario;
import net.unjfsc.model.Vacante;
import net.unjfsc.service.ICategoriasService;
import net.unjfsc.service.IUsuariosService;
import net.unjfsc.service.IVacantesServices;

@Controller
public class HomeController {
	
	@Autowired
	private ICategoriasService serviceCategoria;
	
	@Autowired
	private IVacantesServices serviceVacante;
	
	@Autowired
	private IUsuariosService serviceUsuario;
		
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	@GetMapping("/signup")
	public String registrarse(Usuario usuario) {
		
		
		return "formRegistro";
	}
	
	@PostMapping("/signup")
	public String guardarRegistro(Usuario usuario, RedirectAttributes attributes) {
		String pwdPlano=usuario.getPassword();
		String pwdEncriptado=passwordEncoder.encode(pwdPlano);
		usuario.setPassword(pwdEncriptado);
		
		usuario.setEstatus(1);
		usuario.setFechaRegistro(new Date());
		Perfil perfil=new Perfil();
		perfil.setId(3);
		
		usuario.agregar(perfil);
		
		serviceUsuario.guardar(usuario);
		
		attributes.addFlashAttribute("msg","Usuario Agregado");
		return "redirect:/usuarios/index";
	}
	
	
	
	
	@GetMapping("/tabla")
	public String mostrarTabla(Model model) {
		List<Vacante> lista=serviceVacante.buscarTodas();
		model.addAttribute("vacantes", lista);
		
		return "tabla";
	}
	
	@GetMapping("/detalle")
	public String mostrarDetalle(Model model) {
		Vacante vacante=new Vacante();
		vacante.setNombre("Ingeniero de Comunicaciones");
		vacante.setDescripcion("Se solicita ingeniero para dar soporte a intranet");
		vacante.setFecha(new Date());
		vacante.setSalario(9700.0);
		
		model.addAttribute("vacante", vacante);
		return "detalle";
	}
	
	@GetMapping("/listado")
	public String mostrarListado(Model model) {
		List<String> lista=new LinkedList<String>();
		lista.add("Ingeniero de Sistemas");
		lista.add("Auxiliar de Contabilidad");
		lista.add("Vendedor");
		lista.add("Arquitecto");
		
		model.addAttribute("empleos", lista);
		
		return "listado";
	}
	
	@GetMapping("/login" )
	public String mostrarLogin() {
		return "formLogin";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request){
		SecurityContextLogoutHandler logoutHandler = 
		new SecurityContextLogoutHandler();
		logoutHandler.logout(request, null, null);
		return "redirect:/";
	}

	
	@GetMapping("/")
	public String mostrarHome(Model model) {
		//List<Vacante> lista=serviceVacante.buscarTodas();
		//model.addAttribute("vacantes", lista);
		
		return "home";
	}
	
	
	@GetMapping("/index")
	public String mostrarIndex(Authentication auth,HttpSession session) {
		//Autethicationsirve para recuperar el nombre del usuario
		String username=auth.getName();
		//solo nos da el nombre del usuario pero podemos enviar ese dato para obtener mas datos
		// como apellido correo etc
		for(GrantedAuthority rol: auth.getAuthorities()) {
			//devuelve una coleccion dependiendo cuantos roles tiene
			System.out.println("ROL: "+rol.getAuthority());
		}
		
		if(session.getAttribute("usuario")==null){
			Usuario usuario=serviceUsuario.buscarPorUsername(username);
			//setpasword lo vamos a poner null por motivos de seguridad
			usuario.setPassword(null);
			session.setAttribute("usuario", usuario);
		}
		
		System.out.println("nombre del usuario: "+username);
		return "redirect:/";
	}
	
	//con este metado estamos encriptando el texto en la siguiente url y que muestre en pantalla web
	@GetMapping("/bycrypt/{texto}")
	@ResponseBody
	public String encriptar(@PathVariable("texto") String texto) {
		
		return texto+"Encriptado en Bcrypt"+passwordEncoder.encode(texto);
	}
	
	@GetMapping("/search")
	public String buscar(@ModelAttribute("search") Vacante vacante,Model model) {
		System.out.println("Buscando por: "+vacante);
		
		ExampleMatcher matcher=ExampleMatcher.
				// para que se ejecute where descripcion like '%?%'
				matching().withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.contains());                          
				//en el example.of puede haver un solo parametro
		Example<Vacante> example=Example.of(vacante,matcher);
		List<Vacante> lista= serviceVacante.buscarByExample(example);
		model.addAttribute("vacantes",lista);
		return "home";
	}
	
	
	//Init Binder para String si los detecta vacios en el DataBinding los settea a null
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@ModelAttribute
	public void setGenericos(Model model) {
		Vacante vacanteSearch= new Vacante(); 
		vacanteSearch.reset();
		
		model.addAttribute("vacantes", serviceVacante.buscarDestacadas());
		model.addAttribute("categorias",serviceCategoria.buscarTodas());
		model.addAttribute("search", vacanteSearch);
	}
	
	/*private List<Vacante> getVacantes(){
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		List<Vacante> lista=new LinkedList<Vacante>();
		try {
			
			//Creamos la oferta de Trabajo 1.
			Vacante vacante1=new Vacante();
			vacante1.setId(1);
			vacante1.setNombre("Ingeniero civil");
			vacante1.setDescripcion("Solicitamos Ing. Civil para diseñar puente peatonal. ");
			vacante1.setFecha(sdf.parse("08-02-2019"));
			vacante1.setSalario(8500.0);
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
		return lista;
		
	}*/
	
}
