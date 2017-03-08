package es.ibermatica.arquitectura.service;

import java.util.List;

import es.ibermatica.arquitectura.model.User;
import es.ibermatica.arquitectura.util.vo.UserVO;


public interface UserService {
	
	UserVO findById(int id);
	
	UserVO findBySSO(String sso);
	
	void saveUser(UserVO user);
	
	void updateUser(UserVO user);
	
	void deleteUserBySSO(String sso);

	List<UserVO> findAllUsers(); 
	
	boolean isUserSSOUnique(Integer id, String sso);

}