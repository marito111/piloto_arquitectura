package es.ibermatica.arquitectura.controller;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import es.ibermatica.arquitectura.excepcion.BaseArquitecturaException;
import es.ibermatica.arquitectura.util.ErrorEnum;
import es.ibermatica.arquitectura.util.vo.ErrorVO;

@ControllerAdvice
public class ExceptionController {
	
	static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);
	
	@ExceptionHandler(BaseArquitecturaException.class)
	public ResponseEntity<ErrorVO> errorGenerico(BaseArquitecturaException e) {
		logger.error(e.getMessage());
		return new ResponseEntity<ErrorVO> (generaError(e.getCodigo() ,e.getMessage()), HttpStatus.OK);

	}

	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorVO> excepcion(Exception e) {
		logger.error(e.getMessage());
		return new ResponseEntity<ErrorVO>(generaError(ErrorEnum.ERROR_GENERICO.getCode() ,e.getMessage()), HttpStatus.OK);
	}
	
	
	/**
	 * Convert a predefined exception to an HTTP Status code
	 */
	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation")
	@ExceptionHandler(DataIntegrityViolationException.class)
	public void conflict() {
		logger.error("DataIntegrityViolationException");
	}

	/**
	 * Convert a predefined exception to an HTTP Status code and specify the
	 * name of a specific view that will be used to display the error.
	 * 
	 * @return Exception view.
	 */
	@ExceptionHandler({ SQLException.class, DataAccessException.class })
	public String databaseError(Exception exception) {
		logger.error(exception.getMessage());
		return "databaseError";
	}
	
	private ErrorVO generaError(String codigo , String descripcion){
		ErrorVO errorVO = new ErrorVO();
		errorVO.setCodError(codigo);
		errorVO.setDesError(descripcion);
		return errorVO;
	}

	
}

