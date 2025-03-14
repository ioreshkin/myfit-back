package center.myfit.service;

import center.myfit.entity.User;
import center.myfit.entity.Workout;
import center.myfit.entity.WorkoutExercise;
import center.myfit.entity.WorkoutImage;
import center.myfit.exception.NotFoundException;
import center.myfit.mapper.WorkoutExerciseMapper;
import center.myfit.mapper.WorkoutMapper;
import center.myfit.repository.WorkoutExerciseRepository;
import center.myfit.repository.WorkoutRepository;
import center.myfit.starter.dto.WorkoutDto;
import center.myfit.starter.dto.WorkoutImageDto;
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
  private final WorkoutExerciseRepository workoutExerciseRepository;
  private final UserAware<User> userAware;
  private final WorkoutMapper workoutMapper;
  private final WorkoutExerciseMapper workoutExerciseMapper;
  private final UserService userService;

  /** Создание тренировки. */
  @Transactional
  public WorkoutDto createWorkout(WorkoutDto dto) {

    User user = userService.getUser(dto.keycloakId());
    Workout workout = workoutMapper.map(dto, user);
    log.info("создан воркаут {}", workout.toString());

    Workout savedWorkout = workoutRepository.save(workout);
    log.info("сохранено воркаут, присвоен id{}", workout.toString());

    List<WorkoutExercise> workoutExercises = workoutExerciseMapper.map(dto, savedWorkout);
    workoutExerciseRepository.saveAll(workoutExercises);

    return workoutMapper.map(savedWorkout, dto);
  }

  /** Получить все тренировки. */
  public List<WorkoutDto> getAll() {
    log.info("проверяем аутентифицирован ли пользователь");
    User user = userAware.getUser();
    return workoutRepository.findAllByOwner(user).stream().map(workoutExerciseMapper::map).toList();
  }

  /** Обновление тренировки. */
  @Transactional
  public WorkoutDto updateWorkout(Long id, WorkoutDto dto) {
    log.info("проверяем аутентифицирован ли пользователь");
    User user = userService.getUser(dto.keycloakId());

    log.info("ищем существующую тренировку с id={} для пользователя {}", id, user);
    Workout workout =
        workoutRepository
            .findByIdAndOwner(id, user)
            .orElseThrow(
                () ->
                    new RuntimeException(
                        "Тренировка не найдена или " + "не принадлежит пользователю"));
    log.info("треню нашли        {}", workout);

    log.info("Обновляем все поля тренировки");
    workout.setTitle(dto.title());
    workout.setDescription(dto.description());

    WorkoutImage workoutImage = workout.getImage();
    if (workoutImage == null) {
      workoutImage = new WorkoutImage();
      workoutImage.setWorkout(workout);
    }
    workoutImage.setOriginal(dto.image().original());
    workoutImage.setDesktop(dto.image().desktop());
    workoutImage.setMobile(dto.image().mobile());
    workout.setImage(workoutImage);

    List<WorkoutExercise> workoutExercises = workoutExerciseMapper.map(dto, workout);

    log.info("сохраняем все изменения в базу");
    workoutExerciseRepository.deleteAllByWorkout(workout);
    Workout updatedWorkout = workoutRepository.saveAndFlush(workout);
    workoutExerciseRepository.saveAllAndFlush(workoutExercises);

    WorkoutDto workoutDto = workoutMapper.map(updatedWorkout, dto);
    log.info("workoutDto такой {}", workoutDto);
    return workoutDto;
  }

  /** Обновить у тренировки ссылки на изображения. */
  public void updateWorkoutImage(WorkoutImageDto dto) {
    Workout workout =
        workoutRepository
            .findById(dto.workoutId())
            .orElseThrow(
                () -> new NotFoundException("Workout with id = " + dto.workoutId() + "not found"));
    workout.getImage().setMobile(dto.image().mobile());
    workout.getImage().setDesktop(dto.image().desktop());
    workoutRepository.save(workout);
  }
}
