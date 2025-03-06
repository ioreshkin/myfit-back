package center.myfit.repository;

import center.myfit.entity.Workout;
import center.myfit.entity.WorkoutExercise;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/** Репозиторий для WorkoutExercise. */
public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Long> {
  /** Найти все упражнения входящие в тренировку. */
  List<WorkoutExercise> findByWorkout(Workout workout);
}
