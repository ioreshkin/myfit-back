package center.myfit.repository;

import center.myfit.entity.ProgramUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramUserRepository extends JpaRepository<ProgramUser, Long> {
}
