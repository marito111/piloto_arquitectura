package es.ibermatica.arquitectura.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Clase que realiza la redirección al formulario de acceso en caso de intentar entrar a la aplicación sin una autenticación previa en el servidor.
 * @author ibermatica
 *
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

     public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException ae) throws IOException, ServletException {
    
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");

    }
}
