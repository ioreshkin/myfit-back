package center.myfit.repository;

import center.myfit.entity.Program;
import center.myfit.entity.ProgramWorkout;
import center.myfit.entity.Workout;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/** Репозиторий для ProgramWorkout. */
public interface ProgramWorkoutRepository extends JpaRepository<ProgramWorkout, Long> {

  /**
   * Поиск всех программ с данными тренировками.
   *
   * @param workouts тренировки
   * @return список программ
   */
  List<Program> findAllByWorkoutIn(List<Workout> workouts);
}
