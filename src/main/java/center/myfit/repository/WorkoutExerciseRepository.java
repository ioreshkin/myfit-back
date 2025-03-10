package center.myfit.repository;

import center.myfit.entity.Workout;
import center.myfit.entity.WorkoutExercise;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/** Репозиторий для WorkoutExercise. */
public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Long> {

  /** Найти все упражнения входящие в тренировку, в отсортированном порядке. */
  List<WorkoutExercise> findByWorkoutOrderByOrders(Workout workout);

  /** Удалить все упражнения, относящиеся к тренировке. */
  void deleteAllByWorkout(Workout workout);

}
