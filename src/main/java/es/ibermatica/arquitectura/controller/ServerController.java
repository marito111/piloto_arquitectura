package es.ibermatica.arquitectura.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.ibermatica.arquitectura.excepcion.BaseArquitecturaException;
import es.ibermatica.arquitectura.service.UserProfileService;
import es.ibermatica.arquitectura.service.UserService;
import es.ibermatica.arquitectura.util.ErrorEnum;
import es.ibermatica.arquitectura.util.dto.UserDto;
import es.ibermatica.arquitectura.util.vo.UserProfileVO;
import es.ibermatica.arquitectura.util.vo.UserVO;


/**
 * Clase que contiene las operaciones de los servicios REST.
 * @author ibermatica
 *
 */
@RestController
public class ServerController {

	//Un servidor de autenticación autónomo OAuth2 y una aplicación cliente (basada en la web), todas con Spring OAuth2.
	//Tengo un host de inicio de sesión en el servidor Auth con redirección, etc. desde la aplicación cliente usando la configuración 
	//de Spring (a través del formulario de inicio de sesión).

	//--  Inyeccion de las instancias que tenemos en nuestro contexto
	@Autowired
	UserService userService;

	@Autowired
	UserProfileService userProfileService;


	/**
	 * Maneja la solicitud POST para guardar el usuario en la base de datos. También valida la entrada del usuario.
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = { "/restoauth2/nuevousuario" },  method = RequestMethod.POST)
	public ResponseEntity<UserVO> saveUser(@RequestBody UserVO user) {
		if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
			return new ResponseEntity<UserVO>(HttpStatus.IM_USED);
		}
		userService.saveUser(user);
		return new ResponseEntity<UserVO> (user, HttpStatus.OK);
	}


	/**
	 * Elimina usuario.
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = { "/restoauth2/elimina-usuario-{ssoId}" }, method = RequestMethod.GET)
	public String deleteUser(@PathVariable String ssoId) {
		userService.deleteUserBySSO(ssoId);
		return "";
	}

	
	/**
	 * Metodo que realiza la peticion de listar todos los usuarios.
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/restoauth2/user", method = RequestMethod.GET)
	public ResponseEntity<UserDto> listAllUsers() throws IOException {
		
		UserDto dto = new UserDto();
		
		List<UserVO> users = userService.findAllUsers();
		if(users.isEmpty()){
			throw new BaseArquitecturaException(ErrorEnum.ERROR_CONSULTA.getCode() , "No se han encontrado resultados en la consulta");
		}else{
			dto.setListaUsuarios(users);
			return  new ResponseEntity<UserDto>(dto, HttpStatus.OK);
		}
		
	}

	/**
	 * Metodo que devuelve la lista de roles.
	 * @return
	 */
	@RequestMapping(value = "/rest/roles", method = RequestMethod.GET)
	public ResponseEntity<List<UserProfileVO>> listAllRoles() {
		List<UserProfileVO> roles = userProfileService.findAll();
		if(roles.isEmpty()){
			throw new BaseArquitecturaException(ErrorEnum.ERROR_CONSULTA.getCode() , "No se han encontrado resultados en la consulta");
		}
		return new ResponseEntity<List<UserProfileVO>>(roles, HttpStatus.OK);
	}

	/**
	 * Actualiza a un usuario.
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = { "/restoauth2/edita-usuario-{ssoId}" }, method = RequestMethod.GET)
	public ResponseEntity<UserVO> editUser(@PathVariable String ssoId) {
		UserVO user = userService.findBySSO(ssoId);
		if(user == null){
			throw new BaseArquitecturaException(ErrorEnum.ERROR_CONSULTA.getCode() , "No se han encontrado resultados en la consulta");
		}
		return new ResponseEntity<UserVO> (user, HttpStatus.OK);
	}



	/**
	 * Maneja la solicitud POST - Actualiza a un usuario
	 */
	@RequestMapping(value = { "/restoauth2/edita-usuario-{ssoId}" }, method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public UserVO updateUser(@RequestBody UserVO user,  @PathVariable String ssoId) {
		userService.updateUser(user);
		return user;
	}

	/**
	 * Metodo que realiza el logout.
	 */
	@RequestMapping(value="/restoauth2/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login.do?logout";
	}
	



}