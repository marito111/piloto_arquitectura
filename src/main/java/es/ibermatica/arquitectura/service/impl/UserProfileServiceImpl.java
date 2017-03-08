package es.ibermatica.arquitectura.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ibermatica.arquitectura.dao.UserProfileRepository;
import es.ibermatica.arquitectura.service.UserProfileService;
import es.ibermatica.arquitectura.util.UtilDto;
import es.ibermatica.arquitectura.util.vo.UserProfileVO;
import jersey.repackaged.com.google.common.collect.Lists;


@Service("userProfileService")
@Transactional
public class UserProfileServiceImpl implements UserProfileService{
	
	
	@Autowired(required = true)
	UserProfileRepository userProfileRepository;
	
	public UserProfileVO findById(int id) {
		return UtilDto.convertirToVO(userProfileRepository.findById(id));
	}

	public UserProfileVO findByType(String type){
		return UtilDto.convertirToVO(userProfileRepository.findByType(type));
	}

	public List<UserProfileVO> findAll() {
		return UtilDto.convertirToVO(Lists.newArrayList(userProfileRepository.findAll()));
	}
}
