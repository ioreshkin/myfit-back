package center.myfit.repository;

import center.myfit.entity.CoachUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachUserRepository extends JpaRepository<CoachUser, Long> {
}
