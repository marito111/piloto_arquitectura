package es.ibermatica.arquitectura.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.ibermatica.arquitectura.model.UserProfile;

@Repository
@Qualifier("userProfileRepository")
public interface UserProfileRepository extends JpaRepository <UserProfile, Integer>{

	UserProfile findByType(String type);
	
	UserProfile findById(int id);
	
}
