package es.ibermatica.arquitectura.util;

public enum ErrorEnum {

	ERROR_GENERICO("ERROR_GENERICO"),
	ERROR_CONSULTA("ERROR_CONSULTA");
	
	private String code;

	ErrorEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
