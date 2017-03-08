package es.ibermatica.arquitectura.util.vo;

import java.io.Serializable;

public class ErrorVO  implements Serializable{

	/**
	 * C�digo Identificativo de Serializaci�n.
	 */
	private static final long serialVersionUID = 154066264688182479L;
	
	/**
	 * C�digo de error.
	 */
	private String codError;
	/**
	 * Descripci�n del error.
	 */
	private String desError;
	
	/**
	 * Excepcion.
	 */
	private String exception;
	/**
	 * M�todo que devuelve el valor de la variable codError.
	 * @return valor de la variable codError.
	 */
	public final String getCodError() {
		return codError;
	}
	
	/**
	 * M�todo que asigna valor a la variable codError.
	 * @param pCodError valor a asignar.
	 */
	public final void setCodError(String pCodError) {
		this.codError = pCodError;
	}
	/**
	 * M�todo que devuelve el valor de la variable desError.
	 * @return valor de la variable desError.
	 */
	public final String getDesError() {
		return desError;
	}
	
	/**
	 * M�todo que asigna valor a la variable desError.
	 * @param pDesError valor a asignar.
	 */
	public final void setDesError(String pDesError) {
		this.desError = pDesError;
	}

	/**
	 * M�todo que obtiene valor a la variable exception.
	 * @return exception
	 */
	public final String getException() {
		return exception;
	}

	/**
	 * M�todo que asigna valor a la variable sexception.
	 * @param sexception sexception
	 */
	public final void setException(final String sexception) {
		this.exception = sexception;
	}
}