package center.myfit.service;

import center.myfit.dto.UserWorkoutExerciseDto;
import center.myfit.entity.Exercise;
import center.myfit.entity.User;
import center.myfit.entity.UserWorkoutExercise;
import center.myfit.entity.WorkoutExercise;
import center.myfit.exception.UserNotAuthenticated;
import center.myfit.mapper.ExerciseMapper;
import center.myfit.repository.ExerciseRepository;
import center.myfit.repository.UserRepository;
import center.myfit.repository.UserWorkoutExerciseRepository;
import center.myfit.repository.WorkoutExerciseRepository;
import center.myfit.starter.dto.ExerciseDto;
import center.myfit.starter.service.UserAware;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Сервис работы с упражнениями. */
@Service
@RequiredArgsConstructor
public class ExerciseService {
  private final ExerciseRepository exerciseRepository;
  private final UserRepository userRepository;
  private final WorkoutExerciseRepository workoutExerciseRepository;
  private final UserWorkoutExerciseRepository userWorkoutExerciseRepository;
  private final UserAware<User> userAware;
  private final ExerciseMapper exerciseMapper;

  /** Создание упражнения. */
  public ExerciseDto create(ExerciseDto dto) {
    User user =
        userRepository
            .findUserByKeycloakId(dto.keycloakId())
            .orElseThrow(() -> new UserNotAuthenticated("Пользователь не найден!"));
    Exercise exercise = exerciseMapper.map(dto, user);
    exercise.setOwner(user);
    Exercise saved = exerciseRepository.save(exercise);
    return exerciseMapper.map(saved);
  }

  /** Получить все упражнения. */
  public List<ExerciseDto> getAll() {
    User user = userAware.getUser();
    return exerciseRepository.findAllByOwner(user).stream().map(exerciseMapper::map).toList();
  }

  /** Созранить выполение упражнения. */
  public void saveApproach(UserWorkoutExerciseDto dto) {
    UserWorkoutExercise uwe = new UserWorkoutExercise();
    User user =
        userRepository
            .findById(dto.userId())
            .orElseThrow(
                () -> new RuntimeException("User with id = " + dto.userId() + "not found"));
    WorkoutExercise workoutExercise =
        workoutExerciseRepository
            .findById(dto.workoutExerciseId())
            .orElseThrow(
                () ->
                    new RuntimeException(
                        "WorkoutExercise with id = " + dto.workoutExerciseId() + "not found"));
    uwe.setRepeats(dto.repeats());
    uwe.setUser(user);
    uwe.setWorkoutExercise(workoutExercise);
    userWorkoutExerciseRepository.save(uwe);
  }

  //    private Exercise map(ExerciseDto dto) {
  //        Exercise exercise = new Exercise();
  //        exercise.setTitle(dto.title());
  //        exercise.setDescription(dto.description());
  //        exercise.setPictureUrl(dto.pictureUrl());
  //        exercise.setVideoUrl(dto.videoUrl());
  //        return exercise;
  //    }

  //    private ExerciseDto map(Exercise exercise) {
  //        return new ExerciseDto(exercise.getId(), exercise.getTitle(), exercise.getDescription(),
  //                exercise.getPictureUrl(), exercise.getVideoUrl());
  //    }
}
