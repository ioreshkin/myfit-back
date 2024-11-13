package center.myfit.repository;

import center.myfit.entity.Program;
import center.myfit.entity.ProgramWorkout;
import center.myfit.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramWorkoutRepository extends JpaRepository<ProgramWorkout, Long> {
    List<Program> findAllByWorkout(List<Workout> workouts);
}
