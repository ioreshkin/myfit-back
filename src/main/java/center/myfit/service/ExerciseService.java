package center.myfit.service;

import center.myfit.dto.UserWorkoutExerciseDto;
import center.myfit.entity.Exercise;
import center.myfit.entity.User;
import center.myfit.entity.UserWorkoutExercise;
import center.myfit.entity.WorkoutExercise;
import center.myfit.exception.NotFoundException;
import center.myfit.mapper.ExerciseMapper;
import center.myfit.repository.ExerciseRepository;
import center.myfit.repository.UserWorkoutExerciseRepository;
import center.myfit.repository.WorkoutExerciseRepository;
import center.myfit.starter.dto.ExerciseDto;
import center.myfit.starter.dto.ExerciseImageDto;
import center.myfit.starter.service.UserAware;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Сервис работы с упражнениями. */
@Service
@RequiredArgsConstructor
public class ExerciseService {
  private final ExerciseRepository exerciseRepository;
  private final UserService userService;
  private final WorkoutExerciseRepository workoutExerciseRepository;
  private final UserWorkoutExerciseRepository userWorkoutExerciseRepository;
  private final UserAware<User> userAware;
  private final ExerciseMapper exerciseMapper;

  /** Создание упражнения. */
  public ExerciseDto create(ExerciseDto dto) {
    User user = userService.getUser(dto.keycloakId());
    Exercise exercise = exerciseMapper.mapToCreate(dto, user);
    Exercise saved = exerciseRepository.save(exercise);
    return exerciseMapper.map(saved);
  }

  /** Получить все упражнения. */
  public List<ExerciseDto> getAll() {
    User user = userAware.getUser();
    return exerciseRepository.findAllByOwner(user).stream().map(exerciseMapper::map).toList();
  }

  /** Сохранить выполнение упражнения. */
  public void saveApproach(UserWorkoutExerciseDto dto) {
    UserWorkoutExercise uwe = new UserWorkoutExercise();
    User user = userService.getUser(dto.keycloakId());
    WorkoutExercise workoutExercise =
        workoutExerciseRepository
            .findById(dto.workoutExerciseId())
            .orElseThrow(
                () ->
                    new NotFoundException(
                        "WorkoutExercise with id = " + dto.workoutExerciseId() + "not found"));
    uwe.setRepeats(dto.repeats());
    uwe.setUser(user);
    uwe.setWorkoutExercise(workoutExercise);
    userWorkoutExerciseRepository.save(uwe);
  }

  /** Обновить у упражнения ссылки на изображения. */
  public void updateExerciseImage(ExerciseImageDto dto) {
    Exercise exercise =
        exerciseRepository
            .findById(dto.exerciseId())
            .orElseThrow(
                () ->
                    new NotFoundException("Exercise with id = " + dto.exerciseId() + "not found"));
    exercise.getImage().setMobile(dto.image().mobile());
    exercise.getImage().setDesktop(dto.image().desktop());
    exerciseRepository.save(exercise);
  }
}
