package center.myfit.service;

import center.myfit.dto.ExerciseForWorkoutDto;
import center.myfit.dto.WorkoutDto;
import center.myfit.entity.Exercise;
import center.myfit.entity.User;
import center.myfit.entity.Workout;
import center.myfit.entity.WorkoutExercise;
import center.myfit.mapper.WorkoutExerciseMapper;
import center.myfit.mapper.WorkoutMapper;
import center.myfit.repository.ExerciseRepository;
import center.myfit.repository.WorkoutExerciseRepository;
import center.myfit.repository.WorkoutRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkoutService {
  private final WorkoutRepository workoutRepository;
  private final ExerciseRepository exerciseRepository;
  private final WorkoutExerciseRepository workoutExerciseRepository;
  private final UserAware userAware;
  private final WorkoutMapper workoutMapper;
  private final WorkoutExerciseMapper workoutExerciseMapper;

  @Transactional
  public WorkoutDto create(WorkoutDto dto) {
    Workout workout = workoutMapper.map(dto);
    User user = userAware.getUser();
    workout.setOwner(user);
    Workout saved = workoutRepository.save(workout);

    List<WorkoutExercise> workoutExercises =
        dto.exercises().stream()
            .map(
                exerciseForWorkoutDto -> {
                  WorkoutExercise workoutExercise = new WorkoutExercise();
                  workoutExercise.setWorkout(workout);
                  workoutExercise.setSets(exerciseForWorkoutDto.sets());
                  workoutExercise.setRepeats(exerciseForWorkoutDto.repeats());
                  workoutExercise.setOrderNumber(exerciseForWorkoutDto.orderNumber());
                  Exercise exercise =
                      exerciseRepository
                          .findById(exerciseForWorkoutDto.id())
                          .orElseThrow(
                              () ->
                                  new RuntimeException(
                                      "Exercise with id = "
                                          + exerciseForWorkoutDto.id()
                                          + "not found"));
                  workoutExercise.setExercise(exercise);
                  return workoutExercise;
                })
            .toList();

    workoutExerciseRepository.saveAll(workoutExercises);

    return workoutMapper.map(saved);
  }

  public List<WorkoutDto> getAll() {
    User user = userAware.getUser();
    return workoutRepository.findAllByOwner(user).stream().map(workoutMapper::map).toList();
  }

  //    private Workout map(WorkoutDto dto) {
  //        Workout workout = new Workout();
  //        workout.setTitle(dto.title());
  //        workout.setDescription(dto.description());
  //        workout.setDifficulty(dto.difficulty());
  //        return workout;
  //    }
  //
  //    private WorkoutDto map(Workout workout) {
  //        return new WorkoutDto(workout.getId(), workout.getTitle(), workout.getDescription(),
  // workout.getDifficulty(), null);
  //    }

  private WorkoutExercise map(ExerciseForWorkoutDto dto, Workout workout) {
    Exercise exercise =
        exerciseRepository
            .findById(dto.id())
            .orElseThrow(
                () -> new RuntimeException("Exercise with id = " + dto.id() + " not found"));

    return workoutExerciseMapper.map(dto, workout, exercise);
    //        WorkoutExercise workoutExercise = new WorkoutExercise();
    //        workoutExercise.setWorkout(workout);
    //        workoutExercise.setExercise(exercise);
    //        workoutExercise.setRepeats(dto.repeats());
    //        workoutExercise.setSets(dto.sets());
    //        workoutExercise.setOrderNumber(dto.orderNumber());
    //        return workoutExercise;
  }
}
