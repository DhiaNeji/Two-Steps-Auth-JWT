package BusinessLogicServer.app.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import BusinessLogicServer.app.Proxy.AuthenticationServerProxy;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider{

	@Autowired
	private AuthenticationServerProxy AuthenticationServerProxy;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String username=authentication.getName();
		String password=String.valueOf(authentication.getCredentials());
		this.AuthenticationServerProxy.sendAuth(username, password);
		return new UsernamePasswordAuthenticationToken(username, password);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthentication.class.isAssignableFrom(authentication);
	}

}
