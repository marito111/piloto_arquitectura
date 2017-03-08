package es.ibermatica.arquitectura.service;

import java.util.List;

import es.ibermatica.arquitectura.util.vo.UserProfileVO;


public interface UserProfileService {

	UserProfileVO findById(int id);

	UserProfileVO findByType(String type);
	
	List<UserProfileVO> findAll();
	
}
