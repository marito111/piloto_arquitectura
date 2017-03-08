package es.ibermatica.arquitectura.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import es.ibermatica.arquitectura.dao.UserDao;
import es.ibermatica.arquitectura.dao.UserRepository;
import es.ibermatica.arquitectura.model.User;
import es.ibermatica.arquitectura.service.UserService;
import es.ibermatica.arquitectura.util.UtilDto;
import es.ibermatica.arquitectura.util.vo.UserVO;
import jersey.repackaged.com.google.common.collect.Lists;


@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired(required = true)
	UserRepository userRepository;
	

	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public UserVO findById(int id) {
		User user = userRepository.findById(id);
		UserVO userVo = null;
		if(user!=null){
			 userVo = UtilDto.convertirToVO(user);
		}
		return userVo;
	}

	public UserVO findBySSO(String sso) {
		User user = userRepository.findBySsoId(sso);
		UserVO userVo = null;
		if(user!=null){
			 userVo = UtilDto.convertirToVO(user);
		}
		return userVo;
	}

	public void saveUser(UserVO userVO) {
		userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
		User user = UtilDto.convertirToEntidad(userVO);
		userRepository.save(user);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateUser(UserVO user) {
		User entity = userRepository.findById(user.getId());
		if(entity!=null){
			entity.setSsoId(user.getSsoId());
			if(!user.getPassword().equals(entity.getPassword())){
				entity.setPassword(passwordEncoder.encode(user.getPassword()));
			}
			entity.setFirstName(user.getFirstName());
			entity.setLastName(user.getLastName());
			entity.setEmail(user.getEmail());
			if(!user.getUserProfiles().isEmpty())
				entity.setUserProfiles(UtilDto.convertirToEntidad(user.getUserProfiles()));
		}
	}

	
	public void deleteUserBySSO(String sso) {
		userRepository.deleteBySsoId(sso);
	}

	public List<UserVO> findAllUsers() {
		return Lists.newArrayList(UtilDto.convertirListaUserToVO(userRepository.findAll()));
	}

	public boolean isUserSSOUnique(Integer id, String sso) {
		UserVO userVO = findBySSO(sso);
		User user = null;
		if(userVO!=null)
			user = UtilDto.convertirToEntidad(findBySSO(sso));
		return ( user == null || ((id != null) && (user.getId() == id)));
	}
	
}
