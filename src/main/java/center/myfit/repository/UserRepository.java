package center.myfit.repository;

import center.myfit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    boolean existsByInvite(Integer invite);

    Optional<User> findByInvite(Integer invite);
}
