package es.ibermatica.arquitectura.excepcion;

public class BaseArquitecturaException extends RuntimeException {

private static final long serialVersionUID = -3556835217293510192L;
	
	private String codigo;

	/**
	 * Constructor.
	 * @param t excepcion original
	 */
	public BaseArquitecturaException(Throwable t) {
		super(t);
	}

	/**
	 * Constructor.
	 * @param msg mensaje de error
	 * @param t excepcion original
	 */
	public BaseArquitecturaException(String msg, Throwable t) {
		super(msg, t);
	}

	/**
	 * Constructor.
	 * @param scodigo  de error
	 * @param t excepcion original
	 * @param msg mensaje error
	 */
	public BaseArquitecturaException(String scodigo, String msg, Throwable t) {
		this(msg, t);
		this.codigo = scodigo;
	}
	
	/**
	 * Constructor.
	 * @param scodigo  de error
	 * @param msg mensaje error
	 */
	public BaseArquitecturaException(String scodigo, String msg) {
		this(msg);
		this.codigo = scodigo;
	}
	
	/**
	 * Constructor.
	 * @param msg mensaje de error
	 */
	public BaseArquitecturaException(String msg) {
		super(msg);
	}

	/**
	 * Metodo que se encarga de obtener el valor del atributo codigo.
	 * @return codigo.
	 */
	public final String getCodigo() {
		return codigo;
	}

	/**
	 * Metodo que se encarga de establecer el valor del atributo codigo.
	 * @param scodigo atributo codigo
	 */
	public final void setCodigo(final String scodigo) {
		this.codigo = scodigo;
	}
	

}
