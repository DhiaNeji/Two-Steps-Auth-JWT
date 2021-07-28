package BusinessLogicServer.app.Config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.query.criteria.internal.expression.function.SubstringFunction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import BusinessLogicServer.app.Service.UsernamePasswordAuthentication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Value("${jwt.signing.key}")
	private String signingKey;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String jwt = request.getHeader("Authorization");
		int length=jwt.length();
		jwt=jwt.substring(7,length);
		SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
		String username = String.valueOf(claims.get("username"));
		GrantedAuthority a = new SimpleGrantedAuthority("user");
		List<GrantedAuthority> l=new ArrayList<GrantedAuthority>();
		l.add(a);
		Authentication auth = new UsernamePasswordAuthentication(username,null,l);
		SecurityContextHolder.getContext().setAuthentication(auth);		
		filterChain.doFilter(request, response);
	}
	
	@Override
	 protected boolean shouldNotFilter(HttpServletRequest request) {
	
		return request.getServletPath().equals("/login");
	 
	}
}
