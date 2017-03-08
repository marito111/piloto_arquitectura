package es.ibermatica.arquitectura.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * El servidor se personaliza extendiendo la clase AuthorizationServerConfigurerAdapter que proporciona implementaciones 
 * de métodos vacíos para la interfaz AuthorizationServerConfigurer
 *
 */

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final String RESOURCE_ID = "OAUTH2_SERVER";

    /**
     * Almacena tokens en memoria
     */
    @Autowired
    private TokenStore tokenStore;

    /**
     * Handler de aprobación de usuario.
     */
    @Autowired
    private UserApprovalHandler userApprovalHandler;

    /**
     *  Crea una lista de tokens para AuthorizationServerEndpointsConfigurer.
     */
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    /**
     *  Autorizaciones permitidas para el cliente "client-login/secret" .
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
            .withClient("client-login")
            .resourceIds(RESOURCE_ID)
            .authorizedGrantTypes("password", "authorization_code", "implicit")
            .authorities("ROLE_CLIENT")
            .scopes("read", "write", "trust")
            .secret("secret")
            .accessTokenValiditySeconds(240);
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    /**
     * Configura las características no relacionadas con la seguridad de los endpoint del servidor , como almacén de tokens
     * personalizaciones de tokens, aprobaciones de usuarios y tipos de concesión.
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore).userApprovalHandler(userApprovalHandler)
                .authenticationManager(authenticationManager);
    }

    /**
     * Configura la seguridad del servidor del endpoint /oauth/token.
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.realm(RESOURCE_ID + "/client");
    }
}
