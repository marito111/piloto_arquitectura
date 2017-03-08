package es.ibermatica.arquitectura.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Esta es la clase de reemplazo para web.xml
 * No necesitamos crear contextos manualmente sino simplemente configurar las clases de configuraci�n apropiadas en los m�todos
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
         * Cuando examinamos c�mo configurar la seguridad web mediante la configuraci�n del espacio de nombres, 
         * usamos un DelegatingFilterProxy con el nombre "springSecurityFilterChain".
         * Ahora deber�a ser capaz de ver que este es el nombre del FilterChainProxy que se crea por el espacio de nombres.
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
