package es.ibermatica.arquitectura.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.ibermatica.arquitectura.model.User;

@Repository
@Qualifier("userRepository")
public interface UserRepository extends JpaRepository <User, Integer>{

	User findById(int id);
	
	User findBySsoId(String sso);
	
	void deleteBySsoId(String sso);
	
}
