package TokenBased.app.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import TokenBased.app.Entity.Otp;

public interface OtpRepository extends JpaRepository<Otp,String>{

	Optional<Otp> findOtpByUsername(String username);
}
