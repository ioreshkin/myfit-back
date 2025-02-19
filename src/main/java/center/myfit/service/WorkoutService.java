package center.myfit.service;

import center.myfit.entity.User;
import center.myfit.entity.Workout;
import center.myfit.entity.WorkoutExercise;
import center.myfit.entity.WorkoutImage;
import center.myfit.mapper.WorkoutExerciseMapper;
import center.myfit.mapper.WorkoutMapper;
import center.myfit.repository.ExerciseRepository;
import center.myfit.repository.WorkoutExerciseRepository;
import center.myfit.repository.WorkoutRepository;
import center.myfit.starter.dto.WorkoutDto;
import center.myfit.starter.service.UserAware;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** Сервис работы с тренировками. */
@Slf4j
@Service
@RequiredArgsConstructor
public class WorkoutService {
  private final WorkoutRepository workoutRepository;
  private final ExerciseRepository exerciseRepository;
  private final WorkoutExerciseRepository workoutExerciseRepository;
  private final UserAware<User> userAware;
  private final WorkoutMapper workoutMapper;
  private final WorkoutExerciseMapper workoutExerciseMapper;
  private final UserService userService;

  /** Создание тренировки. */
  @Transactional
  public WorkoutDto  create(WorkoutDto dto) {
    //TODO объясни пожалуйста разницу в варианте когда так
    //это реализовано в ExerciseController +
    // тех юзер, здесь так же сделать??

    log.info("пробуем создать юзера ");
    //User user = userAware.getUser();
    User user = userService.getUser(dto.keycloakId());
    log.info("создан юзер {}", user.toString());
    Workout workout = workoutMapper.map(dto, user);

    WorkoutImage workoutImage = new WorkoutImage();
    workoutImage.setOriginal(dto.image().original());
    workout.setImage(workoutImage);
    workoutImage.setWorkout(workout);

    log.info("создан воркаут {}", workout.toString());
    Workout savedWorkout = workoutRepository.save(workout); // тут воркаукт вместе с вернувшимся id
    log.info("сохранено воркаут {}", workout);

    List<WorkoutExercise> workoutExercises = workoutExerciseMapper.map(dto, savedWorkout);
    log.info("Вот такой  лист сейчас уйдет в базу{}", workoutExercises.toString());
    workoutExerciseRepository.saveAll(workoutExercises);

    return workoutMapper.map(savedWorkout, dto);
  }

  /** Получить все тренировки. */
//  public List<WorkoutDto> getAll() {
//    User user = userAware.getUser();
//    return workoutRepository.findAllByOwner(user).stream().map(workoutMapper::map).toList();
//  }
}
