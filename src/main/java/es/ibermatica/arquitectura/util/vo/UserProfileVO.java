package es.ibermatica.arquitectura.util.vo;

import java.io.Serializable;

import es.ibermatica.arquitectura.util.UserProfileType;

public class UserProfileVO implements Serializable{

	private Integer id;	

	private String type = UserProfileType.USER.getUserProfileType();
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	@Override
	public String toString() {
		return "UserProfile [id=" + id + ", type=" + type + "]";
	}




}
