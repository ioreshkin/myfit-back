package center.myfit.repository;

import center.myfit.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;

/** Репозиторий для Program. */
public interface ProgramRepository extends JpaRepository<Program, Long> {}
