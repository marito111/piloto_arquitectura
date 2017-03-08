package es.ibermatica.arquitectura.config;

import java.util.Arrays;

import org.h2.server.web.WebServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import es.ibermatica.arquitectura.security.AccessConfirmationController;
import es.ibermatica.arquitectura.security.AdminController;
import es.ibermatica.arquitectura.security.UserApprovalHandler;

/**
 *  Clase que indica que se trata de una aplicación basada en spring-boot.
 *
 */
@SpringBootApplication(scanBasePackages={"es.ibermatica.arquitectura"})
@EntityScan(basePackages = {"es.ibermatica.arquitectura.model"})
@EnableJpaRepositories(basePackages = {"es.ibermatica.arquitectura.dao"})
@EnableTransactionManagement
@EnableWebMvc
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	 public static void main(String[] args) {
	    SpringApplication.run(WebMvcConfig.class, args);
	  }
	 
	 
	 /**
	  * Registramos H2-BBDD
	  * @return
	  */
 	@Bean
    ServletRegistrationBean h2servletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
        registrationBean.addUrlMappings("/h2-console/*");
        return registrationBean;
    }
	 
	 /**
	  * Permite extraer parametrizaciones del fichero de configuración de Spring y ubicarlas en 
	  * ficheros de propiedades (application.properties).
	  *  @return
	  */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * Permite el uso de varios tipos de ViewResolvers, así como de formatear la salida de datos (en este caso en JSON).
     * @return
     * @throws Exception
     */
    @Bean
    public ContentNegotiatingViewResolver contentViewResolver() throws Exception {
        ContentNegotiationManagerFactoryBean contentNegotiationManager = new ContentNegotiationManagerFactoryBean();
        contentNegotiationManager.addMediaType("json", MediaType.APPLICATION_JSON);

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");

        MappingJackson2JsonView defaultView = new MappingJackson2JsonView();
        defaultView.setExtractValueFromSingleKeyModel(true);

        ContentNegotiatingViewResolver contentViewResolver = new ContentNegotiatingViewResolver();
        contentViewResolver.setContentNegotiationManager(contentNegotiationManager.getObject());
        contentViewResolver.setViewResolvers(Arrays.<ViewResolver>asList(viewResolver));
        contentViewResolver.setDefaultViews(Arrays.<View>asList(defaultView));
        
        return contentViewResolver;
    }

    @Bean
    public AccessConfirmationController accessConfirmationController(ClientDetailsService clientDetailsService, ApprovalStore approvalStore) {
        AccessConfirmationController accessConfirmationController = new AccessConfirmationController();
        accessConfirmationController.setClientDetailsService(clientDetailsService);
        accessConfirmationController.setApprovalStore(approvalStore);
        return accessConfirmationController;
    }


    /**
     * Metodo que recibe los valores necesarios para la clase AdminController
     * @param tokenStore
     * @param consumerTokenServices
     * @param userApprovalHandler
     * @return
     */
    @Bean
    public AdminController adminController(TokenStore tokenStore, ConsumerTokenServices consumerTokenServices,
                                           UserApprovalHandler userApprovalHandler) {
        AdminController adminController = new AdminController();
        adminController.setTokenStore(tokenStore);
        adminController.setTokenServices(consumerTokenServices);
        adminController.setUserApprovalHandler(userApprovalHandler);
        return adminController;
    }

    /**
     * spring-boot configurará automáticamente su resolución de vistas, esto nos ayuda a configurar el Servlet default.
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
    /**
     * Establecemos la pagina de inicio.
     */
    @Override
	public void addViewControllers(ViewControllerRegistry registry) {
	    registry.addViewController("/").setViewName("forward:/index.jsp");
	}
    
    /**
     * Configurar ResourceHandlers para servir recursos estáticos como CSS/ Javascript etc...
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }
    
}
