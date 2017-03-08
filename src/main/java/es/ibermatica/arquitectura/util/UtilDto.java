package es.ibermatica.arquitectura.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;

import es.ibermatica.arquitectura.model.User;
import es.ibermatica.arquitectura.model.UserProfile;
import es.ibermatica.arquitectura.util.vo.UserProfileVO;
import es.ibermatica.arquitectura.util.vo.UserVO;

public class UtilDto {
	
	 private static ModelMapper modelMapper = new ModelMapper();

    
    public static User convertirToEntidad(UserVO vo) {
    	User post = modelMapper.map(vo, User.class);
        return post;
    }
    
    public static UserVO convertirToVO(User entidad) {
    	UserVO post = modelMapper.map(entidad, UserVO.class);
        return post;
    }
    
    public static UserProfile convertirToEntidad(UserProfileVO vo) {
    	UserProfile post = modelMapper.map(vo, UserProfile.class);
        return post;
    }
    
    public static UserProfileVO convertirToVO(UserProfile entidad) {
    	UserProfileVO post = modelMapper.map(entidad, UserProfileVO.class);
        return post;
    }
    
    public static Set<UserProfile> convertirToEntidad(Set<UserProfileVO> listaUserProfileVO) {
    	Set<UserProfile> listaUserProfile =  new HashSet<UserProfile>();
    	for(UserProfileVO vo : listaUserProfileVO){
    		listaUserProfile.add(convertirToEntidad(vo));
    	}
        return listaUserProfile;
    }
    
    public static Set<UserProfileVO> convertirToVO(Set<UserProfile> listaEntidad) {
    	Set<UserProfileVO> listaVO =  new HashSet<UserProfileVO>();
    	for(UserProfile entidad : listaEntidad){
    		listaVO.add(convertirToVO(entidad));
    	}
        return listaVO;
    }

    public static List<User> convertirListaUserVOToEntidad(List<UserVO> listaVO) {
    	List<User> listaEntidad =  new ArrayList<User>();
    	for(UserVO vo : listaVO){
    		listaEntidad.add(convertirToEntidad(vo));
    	}
        return listaEntidad;
    }
    
    public static List<UserVO> convertirListaUserToVO(List<User> listaEntidad) {
    	List<UserVO> listaVO =  new ArrayList<UserVO>();
    	for(User entidad : listaEntidad){
    		listaVO.add(convertirToVO(entidad));
    	}
        return listaVO;
    }
    
    public static List<UserProfile> convertirToEntidad(List<UserProfileVO> listaVO) {
    	List<UserProfile> listaEntidad =  new ArrayList<UserProfile>();
    	for(UserProfileVO vo : listaVO){
    		listaEntidad.add(convertirToEntidad(vo));
    	}
        return listaEntidad;
    }
    
    public static List<UserProfileVO> convertirToVO(List<UserProfile> listaEntidad) {
    	List<UserProfileVO> listaVO =  new ArrayList<UserProfileVO>();
    	for(UserProfile entidad : listaEntidad){
    		listaVO.add(convertirToVO(entidad));
    	}
        return listaVO;
    }
    
}
