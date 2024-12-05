package center.myfit.repository;

import center.myfit.entity.Program;
import center.myfit.entity.ProgramWorkout;
import center.myfit.entity.Workout;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramWorkoutRepository extends JpaRepository<ProgramWorkout, Long> {
  List<Program> findAllByWorkoutIn(List<Workout> workouts);
}
