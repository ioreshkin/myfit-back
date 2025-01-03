package center.myfit.repository;

import center.myfit.entity.WorkoutExercise;
import org.springframework.data.jpa.repository.JpaRepository;

/** Репозиторий для WorkoutExercise. */
public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Long> {}
