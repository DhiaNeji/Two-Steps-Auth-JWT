package BusinessLogicServer.app.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import BusinessLogicServer.app.Service.OtpAuthenticationProvider;
import BusinessLogicServer.app.Service.UsernamePasswordAuthenticationProvider;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private InitialAuthenticationFilter initialAuthenticationFilter;
	
	@Autowired
	private JwtAuthenticationFilter JwtAuthenticationFilter;
	
	@Autowired
	private OtpAuthenticationProvider otpAuthenticationProvider;
	
	@Autowired
	private UsernamePasswordAuthenticationProvider UsernamePasswordAuthenticationProvider;
	
	@Bean
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}
	
	 @Override
	 protected void configure(AuthenticationManagerBuilder auth) {
	 
		 auth.authenticationProvider(UsernamePasswordAuthenticationProvider).authenticationProvider(otpAuthenticationProvider);
	 
	 }
	
	 @Override
	protected void configure(HttpSecurity http) throws Exception {
	
		 http.csrf().disable();
		 http.addFilterAt(initialAuthenticationFilter,BasicAuthenticationFilter.class)
		 .addFilterAfter(JwtAuthenticationFilter,BasicAuthenticationFilter.class);
		 http.authorizeRequests().anyRequest().authenticated();
	 }
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
}
