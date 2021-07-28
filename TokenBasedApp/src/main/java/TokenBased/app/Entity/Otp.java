package TokenBased.app.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
public class Otp {
	
	@Id
	private String username;
	private String code;
	
	public Otp(String username,String code)
	{
		this.username=username;
		this.code=code;
	}

	public Otp() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code=code;
	}
}
