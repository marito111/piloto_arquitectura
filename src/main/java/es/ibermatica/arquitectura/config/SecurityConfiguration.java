package es.ibermatica.arquitectura.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import es.ibermatica.arquitectura.security.UserApprovalHandler;

/**
 * Clase que indica a la configuración de Spring que se van a redefinir métodos de la clase WebSecurityConfigurerAdapter.
 * Con seguridad básica usuario/password implementada.
 * 
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("customUserDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	private TokenStore tokenStore;


	/**
	 * Recupera de base de datos la información del usuario que se quiere loguear. 
	 * Entre los detalles del usuario, se encuentran los “authorities”, es decir, los posibles roles que tendrá el usuario. 
	 * Por lo tanto, en el momento de recuperación y creación de ese objeto, es necesario también indicar los roles correctos. 
	 * De esta forma, el AuthenticationProvider ya tendrá todos los datos necesarios para crear el UsernamePasswordAuthenticationToken 
	 * y el AuthenticationManager sabrá si el usuario puede o no acceder a la url que solicita
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//        auth.inMemoryAuthentication().withUser("mario").password("mario").roles("ADMIN", "USER",  "CLIENT");
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());
	}


	/**
	 * Se encarga de ver si una determinada petición ya estaba aprobada por el usuario
	 * Carga Perezosa.
	 * @return
	 * @throws Exception
	 */
	@Bean
	@Lazy
	@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
	public UserApprovalHandler userApprovalHandler() throws Exception {
		UserApprovalHandler handler = new UserApprovalHandler();
		handler.setApprovalStore(approvalStore());
		handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
		handler.setClientDetailsService(clientDetailsService);
		handler.setUseApprovalStore(true);
		return handler;
	}

	@Bean
	public ApprovalStore approvalStore() throws Exception {
		TokenApprovalStore store = new TokenApprovalStore();
		store.setTokenStore(tokenStore);
		return store;
	}

	/**
	 * Proveedor DAO para acceder a la informacion de usuario.
	 * @return
	 */
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	/**
	 * Encripta la password del usuario.
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Recursos no securizados.
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/webjars/**", "/images/**",
				"/oauth/uncache_approvals", "/oauth/cache_approvals");
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * Metodo que configura el acceso a los recursos dependiendo del rol.
	 * 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//-- se permite libre acceso a los siguientes recursos
		http.authorizeRequests().antMatchers("/login.jsp", "/rest/**", "/h2-console/**", "/static/**").permitAll()
		//-- y a los demas si al menos es un USER
		.and().authorizeRequests().anyRequest().hasRole("USER")
		//-- redireccionamos con un error si es un acceso denegado
		.and().exceptionHandling().accessDeniedPage("/login.jsp?authorization_error=true")
		 /* implementamos protección CSRF (deshabilitada) para conseguir que la solicitud enviada con la redirección
		 incluya un valor que asegure que el usuario esta autenticado (por ejemplo, un hash de la cookie de sesión ... ] */
		.and().csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize")).disable()
		//-- configuramos el logout
		.logout().logoutSuccessUrl("/index.jsp").logoutUrl("/logout.do")
		//-- configuramos el login
		.and().formLogin()
		.usernameParameter("j_username")
		.passwordParameter("j_password")
		.failureUrl("/login.jsp?authentication_error=true")
		.loginPage("/login.jsp")
		.loginProcessingUrl("/login.do");
		
		//-- esto es necesario para poder ver la consola de la BBDD - H2
		http.csrf().disable();
		http.headers().frameOptions().disable();
		
	}

}
