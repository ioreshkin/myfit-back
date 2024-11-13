package center.myfit.repository;

import center.myfit.entity.Exercise;
import center.myfit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findAllByOwner(User owner);
}
