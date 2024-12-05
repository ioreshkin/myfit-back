package center.myfit.repository;

import center.myfit.entity.CoachUser;
import center.myfit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachUserRepository extends JpaRepository<CoachUser, Long> {
  boolean existsByCoachAndFollower(User coach, User follower);
}
