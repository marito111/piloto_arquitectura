package es.ibermatica.arquitectura;
 
import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import es.ibermatica.arquitectura.config.WebMvcConfig;
import es.ibermatica.arquitectura.model.User;
import es.ibermatica.arquitectura.model.UserProfile;
import es.ibermatica.arquitectura.util.UserProfileType;
import es.ibermatica.arquitectura.util.vo.AuthTokenInfo;
 
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebMvcConfig.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SpringRestClient {
 
	static final Logger logger = LoggerFactory.getLogger(SpringRestClient.class);
   
    public  final String REST_SERVICE_URI = "http://localhost:8080/OAUTH2_SERVER/restoauth2";
    
    public  final String AUTH_SERVER_URI = "http://localhost:8080/OAUTH2_SERVER/oauth/token";
    
    public  final String QPM_PASSWORD_GRANT = "?grant_type=password&username=mario&password=mario";
    
    public  final String QPM_ACCESS_TOKEN = "?access_token=";
   
    @Test
	public void aTestDefaultSettings() throws Exception {
			WebMvcConfig.main(new String[] { });
    }
    
    
    /*
     * Send a GET request to get list of all users.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void listAllUsers(){
    	
    	AuthTokenInfo tokenInfo = sendTokenRequest();
    	
    	Assert.notNull(tokenInfo, "Authenticate first please......");

    	logger.info("\nTesting listAllUsers API-----------");
        RestTemplate restTemplate = new RestTemplate(); 
        
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
       
        ResponseEntity<Object> response = restTemplate.exchange(REST_SERVICE_URI+"/user/"+QPM_ACCESS_TOKEN+tokenInfo.getAccess_token(), HttpMethod.GET, request, Object.class);
        LinkedHashMap<String, Object> usersMap = (LinkedHashMap<String, Object>)response.getBody();
        
        if(usersMap!=null){
                logger.info("usersMap:"+usersMap);
        }else{
            logger.info("No user exist----------");
        }
    }
     

     
    /*
     * Send a POST request to create a new user.
     */
    @Test
    public void createUser() {
    	AuthTokenInfo tokenInfo = sendTokenRequest();
    	Assert.notNull(tokenInfo, "Authenticate first please......");
        logger.info("\nTesting create User API----------");
        RestTemplate restTemplate = new RestTemplate();
        User user = new User();
        user.setEmail("aaa");
        user.setFirstName("aaa");
        user.setLastName("aaa");
        user.setPassword("aaa");
        user.setSsoId("aaa");
        UserProfile rol = new UserProfile();
        rol.setId(1);
        rol.setType(UserProfileType.USER.getUserProfileType());
        Set<UserProfile> setRol = new HashSet<UserProfile>();
        setRol.add(rol);
        user.setUserProfiles(setRol);
        HttpEntity<Object> request = new HttpEntity<Object>(user, getHeaders());
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/user/"+QPM_ACCESS_TOKEN+tokenInfo.getAccess_token(),request, User.class);
//        logger.info("Location : "+uri.toASCIIString());
    }
 
    /*
     * Send a GET request to get a specific user.
     */
    @Test
    public void getUser(){
    	AuthTokenInfo tokenInfo = sendTokenRequest();
    	Assert.notNull(tokenInfo, "Authenticate first please......");
        logger.info("\nTesting getUser API----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<User> response = restTemplate.exchange(REST_SERVICE_URI+"/edita-usuario-mario"+QPM_ACCESS_TOKEN+tokenInfo.getAccess_token(), HttpMethod.GET, request, User.class);
        User user = response.getBody();
        logger.info("user:"+user);
    }
    
    /*
     * Send a PUT request to update an existing user.
     */
    @Test
    public void updateUser() {
    	AuthTokenInfo tokenInfo = sendTokenRequest();
    	Assert.notNull(tokenInfo, "Authenticate first please......");
        logger.info("\nTesting update User API----------");
        RestTemplate restTemplate = new RestTemplate();
        User user  = new User();
        user.setId(1);
        user.setEmail("aaa");
        user.setFirstName("mario2");
        user.setLastName("mario2");
        user.setPassword("mario");
        user.setSsoId("mario");
        HttpEntity<Object> request = new HttpEntity<Object>(user, getHeaders());
        ResponseEntity<User> response = restTemplate.exchange(REST_SERVICE_URI+"/edita-usuario-mario"+QPM_ACCESS_TOKEN+tokenInfo.getAccess_token(), HttpMethod.PUT, request, User.class);
        logger.info("response.getBody():"+response.getBody());
    }
 
    /*
     * Send a DELETE request to delete a specific user.
     */
    @Test
    public void deleteUser() {
    	AuthTokenInfo tokenInfo = sendTokenRequest();
    	Assert.notNull(tokenInfo, "Authenticate first please......");
        logger.info("\nTesting delete User API----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        restTemplate.exchange(REST_SERVICE_URI+"/elimina-usuario-pepe"+QPM_ACCESS_TOKEN+tokenInfo.getAccess_token(),HttpMethod.DELETE, request, User.class);
    }
 
    /*
     * Prepare HTTP Headers.
     */
    private static HttpHeaders getHeaders(){
    	HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	return headers;
    }
    
    /*
     * Add HTTP Authorization header, using Basic-Authentication to send client-credentials.
     */
    private static HttpHeaders getHeadersWithClientCredentials(){
    	String plainClientCredentials="client-login:secret";
    	String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));
    	
    	HttpHeaders headers = getHeaders();
    	headers.add("Authorization", "Basic " + base64ClientCredentials);
    	return headers;
    }    
    
    /*
     * Send a POST request [on /oauth/token] to get an access-token, which will then be send with each request.
     */
    @SuppressWarnings({ "unchecked"})
	private  AuthTokenInfo sendTokenRequest(){
        RestTemplate restTemplate = new RestTemplate(); 
        
        HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
        ResponseEntity<Object> response = restTemplate.exchange(AUTH_SERVER_URI+QPM_PASSWORD_GRANT, HttpMethod.POST, request, Object.class);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>)response.getBody();
        AuthTokenInfo tokenInfo = null;
        
        if(map!=null){
        	tokenInfo = new AuthTokenInfo();
        	tokenInfo.setAccess_token((String)map.get("access_token"));
        	tokenInfo.setToken_type((String)map.get("token_type"));
        	tokenInfo.setRefresh_token((String)map.get("refresh_token"));
        	tokenInfo.setExpires_in((int)map.get("expires_in"));
        	tokenInfo.setScope((String)map.get("scope"));
        	logger.info("tokenInfo:"+tokenInfo);
        }else{
            logger.info("Error al recuperar el token");
            
        }
        return tokenInfo;
    }
   
}