package TokenBased.app.Service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import TokenBased.app.Entity.Otp;
import TokenBased.app.Entity.User;
import TokenBased.app.Repository.OtpRepository;
import TokenBased.app.Repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private OtpRepository otpRepository;
	
	public List<User> findAllUsers()
	{
		return this.userRepository.findAll();
	}
	
	public void addUser(User user)
	{
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		this.userRepository.save(user);
	}
	
	public void auth(User user)
	{
		Optional<User> o=this.userRepository.findUserByusername(user.getUsername());
		if(o.isPresent())
		{
			User u=o.get();
			if(this.passwordEncoder.matches(user.getPassword(),u.getPassword()))
			{
				renewOpt(u);
			}
			else
				throw new BadCredentialsException("Bad credentials");
		}
		else
		throw new BadCredentialsException("Bad credentials");
	}
	public void renewOpt(User u)
	{
		String code=GenerateCodeUtil.generateCode();
		
		Optional<Otp> o=this.otpRepository.findOtpByUsername(u.getUsername());
		
		if(o.isPresent())
		{
			Otp op=o.get();
			op.setCode(code);
		}
		else
		{
			 Otp otp = new Otp();
			 otp.setUsername(u.getUsername());
			 otp.setCode(code);
			 otpRepository.save(otp);
		}
	}
	
	public boolean check(Otp otpToValidate)
	{
		Optional<Otp> userOtp = otpRepository.findOtpByUsername(otpToValidate.getUsername());
		if (userOtp.isPresent()) {
			Otp otp = userOtp.get();
			if (otpToValidate.getCode().equals(otp.getCode())) {
				 return true;		 
			}
		}
		return false;
	}
}
