package center.myfit.repository;

import center.myfit.entity.Exercise;
import center.myfit.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
  List<Exercise> findAllByOwner(User owner);
}
