package center.myfit.repository;

import center.myfit.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findUserByEmail(String email);

  boolean existsByInvite(Integer invite);

  Optional<User> findByInvite(Integer invite);
}
