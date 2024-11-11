package center.myfit.repository;

import center.myfit.entity.Exercise;
import center.myfit.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<Program, Long> {
}
