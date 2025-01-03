package center.myfit.service;

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
import center.myfit.starter.service.UserAware;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Сервис работы с тренировками. */
@Service
@RequiredArgsConstructor
public class WorkoutService {
  private final WorkoutRepository workoutRepository;
  private final ExerciseRepository exerciseRepository;
  private final WorkoutExerciseRepository workoutExerciseRepository;
  private final UserAware<User> userAware;
  private final WorkoutMapper workoutMapper;
  private final WorkoutExerciseMapper workoutExerciseMapper;

  /** Создание тренировки. */
  @Transactional
  public WorkoutDto create(WorkoutDto dto) {
    Workout workout = workoutMapper.map(dto);
    User user = userAware.getUser();
    workout.setOwner(user);
    Workout saved = workoutRepository.save(workout);

    List<WorkoutExercise> workoutExercises =
        dto.exercises().stream()
            .map(
                exerciseWorkoutDto -> {
                  WorkoutExercise workoutExercise = new WorkoutExercise();
                  workoutExercise.setWorkout(workout);
                  workoutExercise.setSets(exerciseWorkoutDto.sets());
                  workoutExercise.setRepeats(exerciseWorkoutDto.repeats());
                  workoutExercise.setOrderNumber(exerciseWorkoutDto.orderNumber());
                  Exercise exercise =
                      exerciseRepository
                          .findById(exerciseWorkoutDto.id())
                          .orElseThrow(
                              () ->
                                  new RuntimeException(
                                      "Exercise with id = "
                                          + exerciseWorkoutDto.id()
                                          + "not found"));
                  workoutExercise.setExercise(exercise);
                  return workoutExercise;
                })
            .toList();

    workoutExerciseRepository.saveAll(workoutExercises);

    return workoutMapper.map(saved);
  }

  /** Получить все тренировки. */
  public List<WorkoutDto> getAll() {
    User user = userAware.getUser();
    return workoutRepository.findAllByOwner(user).stream().map(workoutMapper::map).toList();
  }
}
