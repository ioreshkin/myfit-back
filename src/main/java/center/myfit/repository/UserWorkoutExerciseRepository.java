package center.myfit.repository;

import center.myfit.entity.UserWorkoutExercise;
import org.springframework.data.jpa.repository.JpaRepository;

/** Репозиторий для UserWorkoutExercise. */
public interface UserWorkoutExerciseRepository extends JpaRepository<UserWorkoutExercise, Long> {}
