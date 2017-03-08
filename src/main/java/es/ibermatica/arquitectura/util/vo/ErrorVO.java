package es.ibermatica.arquitectura.util.vo;

import java.io.Serializable;

public class ErrorVO  implements Serializable{

	/**
	 * Código Identificativo de Serialización.
	 */
	private static final long serialVersionUID = 154066264688182479L;
	
	/**
	 * Código de error.
	 */
	private String codError;
	/**
	 * Descripción del error.
	 */
	private String desError;
	
	/**
	 * Excepcion.
	 */
	private String exception;
	/**
	 * Método que devuelve el valor de la variable codError.
	 * @return valor de la variable codError.
	 */
	public final String getCodError() {
		return codError;
	}
	
	/**
	 * Método que asigna valor a la variable codError.
	 * @param pCodError valor a asignar.
	 */
	public final void setCodError(String pCodError) {
		this.codError = pCodError;
	}
	/**
	 * Método que devuelve el valor de la variable desError.
	 * @return valor de la variable desError.
	 */
	public final String getDesError() {
		return desError;
	}
	
	/**
	 * Método que asigna valor a la variable desError.
	 * @param pDesError valor a asignar.
	 */
	public final void setDesError(String pDesError) {
		this.desError = pDesError;
	}

	/**
	 * Método que obtiene valor a la variable exception.
	 * @return exception
	 */
	public final String getException() {
		return exception;
	}

	/**
	 * Método que asigna valor a la variable sexception.
	 * @param sexception sexception
	 */
	public final void setException(final String sexception) {
		this.exception = sexception;
	}
}