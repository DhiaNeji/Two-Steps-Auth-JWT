package BusinessLogicServer.app.Config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import BusinessLogicServer.app.Service.OtpAuthentication;
import BusinessLogicServer.app.Service.UsernamePasswordAuthentication;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class InitialAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Value("${jwt.signing.key}")
	private String signingKey;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		 String username = request.getHeader("username");
		 String password = request.getHeader("password");
		 String code = request.getHeader("code");
		 if (code == null) {
			 UsernamePasswordAuthentication a =new UsernamePasswordAuthentication(username, password);
		 authenticationManager.authenticate(a); 
		 }
		 else
		 {
			 Authentication a=new OtpAuthentication(username, code);
			 authenticationManager.authenticate(a);
			 SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
			 Map<String, String> map=new HashMap<String, String>();
			 map.put(username,username);
			 String jwt = Jwts.builder().setClaims(map).signWith(key).compact();
			 response.setHeader("Authorization", jwt); 
		 }
	}
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return !request.getServletPath().equals("/login");
	}
}
