package TokenBased.app.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import TokenBased.app.Entity.User;

public interface UserRepository extends JpaRepository<User,String>{

	Optional<User> findUserByusername(String username);
}
