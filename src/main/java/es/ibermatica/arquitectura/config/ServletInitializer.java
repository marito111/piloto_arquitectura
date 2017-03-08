package es.ibermatica.arquitectura.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Esta es la clase de reemplazo para web.xml
 * No necesitamos crear contextos manualmente sino simplemente configurar las clases de configuración apropiadas en los métodos
 * getRootConfigClasses () y getServletConfigClasses ()
 */
public class ServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { WebMvcConfig.class };
	}
 
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}
 
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	 @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);

        /*
         * Cuando examinamos cómo configurar la seguridad web mediante la configuración del espacio de nombres, 
         * usamos un DelegatingFilterProxy con el nombre "springSecurityFilterChain".
         * Ahora debería ser capaz de ver que este es el nombre del FilterChainProxy que se crea por el espacio de nombres.
         * 
         * <filter>
    		<filter-name>springSecurityFilterChain</filter-name>
    		<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
		  </filter>
         * 
         * */
        
        DelegatingFilterProxy filter = new DelegatingFilterProxy("springSecurityFilterChain");
        filter.setContextAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher");
        servletContext.addFilter("springSecurityFilterChain", filter).addMappingForUrlPatterns(null, false, "/*");
    }

	
}
