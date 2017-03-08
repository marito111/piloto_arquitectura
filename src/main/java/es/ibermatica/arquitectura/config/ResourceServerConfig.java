package es.ibermatica.arquitectura.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;


/**
 * Clase que se encarga de la configuración del servidor de recursos. 
 * Para ello la clase ha de estar anotada con @EnableResourceServer y extender de ResourceServerConfigurerAdapter.
 *
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private static final String RESOURCE_ID = "OAUTH2_SERVER";

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID);
	}

	/**
	 * Metodo donde debemos especificar, entre otras cosas, las credenciales necesarias para acceder a cada recurso.
	 * La url /oauth/token estará disponible para peticiones anonimas ya que es la usada para solicitar tokens de acceso.
	 * Establecemos los scopes y que perfiles tienen acceso a ciertas operaciones.
	 * Con estas restricciones bloqueamos el acceso a nuestros recursos a cualquier aplicación que no haya sido registrada previamente como cliente autorizado.
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.requestMatchers().antMatchers("/restoauth2/**", /* "/rest/**", */ "/oauth/users/**", "/oauth/clients/**").and().authorizeRequests()
		.antMatchers("/restoauth2/user").access("#oauth2.hasScope('read')")
		.antMatchers("/restoauth2/edita-usuario*").access("#oauth2.hasScope('read')")
		//.antMatchers("/rest/roles").access("#oauth2.hasScope('read')")
		.antMatchers("/restoauth2/logout").access("#oauth2.hasScope('read')")
		.regexMatchers(HttpMethod.DELETE, "/oauth/users/([^/].*?)/tokens/.*")
		.access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('write')")
		.regexMatchers(HttpMethod.GET, "/oauth/clients/.*")
		.access("#oauth2.clientHasRole('ROLE_CLIENT') and #oauth2.isClient() and #oauth2.hasScope('read')");
	}
}
