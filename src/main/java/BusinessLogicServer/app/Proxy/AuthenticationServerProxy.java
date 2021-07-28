package BusinessLogicServer.app.Proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import BusinessLogicServer.app.Entity.User;

@Component
public class AuthenticationServerProxy {

	 @Autowired
	 private RestTemplate rest;
	 
	 @Value("${auth.server.base.url}")
	 private String baseUrl;
	 
	 public void sendAuth(String username,String password) {
	
		String url = baseUrl + "/user/auth";
		User body = new User();
		body.setUsername(username);
		body.setPassword(password);
		HttpEntity<?> request = new HttpEntity<>(body);
		rest.postForEntity(url, request, Void.class);
	 }
	 public boolean sendOTP(String username,String code) {
		 
		String url = baseUrl + "/otp/check";
		User body = new User();
		body.setUsername(username);
		body.setCode(code);
		HttpEntity<User> request = new HttpEntity<User>(body);
		return rest.postForEntity(url, request, Void.class).getStatusCode().equals(HttpStatus.OK);
		
	 }
}
