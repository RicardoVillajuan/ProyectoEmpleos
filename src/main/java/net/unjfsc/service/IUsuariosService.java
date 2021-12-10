package net.unjfsc.service;

import java.util.List;
import net.unjfsc.model.Usuario;

public interface IUsuariosService {

	
	void guardar(Usuario usuario);
	
	// Ejercicio: Método que elimina un usuario de la base de datos.
	void eliminar(Integer idUsuario);
	
	// Ejercicio: Implementar método que recupera todos los usuarios. Usar vista de listUsuarios.html
	List<Usuario> buscarTodos();

	Usuario buscarPorUsername(String username);
}

// Agregar al archivo menu.html el link para acceder al listado de Usuarios y configurar el link del botón Registrarse

