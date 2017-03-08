package es.ibermatica.arquitectura.util.dto;

import java.util.ArrayList;
import java.util.List;

import es.ibermatica.arquitectura.util.vo.ErrorVO;
import es.ibermatica.arquitectura.util.vo.UserVO;

public class UserDto {

	List<UserVO> listaUsuarios;
	
	ErrorVO errorVO;

	public ErrorVO getErrorVO() {
		if(errorVO ==null)errorVO = new ErrorVO();
		return errorVO;
	}

	public void setErrorVO(ErrorVO errorVO) {
		this.errorVO = errorVO;
	}

	public List<UserVO> getListaUsuarios() {
		if(listaUsuarios == null) listaUsuarios = new ArrayList<UserVO> ();
		return listaUsuarios;
	}

	public void setListaUsuarios(List<UserVO> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
	
}
