package center.myfit.service;

import center.myfit.entity.User;
import center.myfit.entity.Workout;
import center.myfit.entity.WorkoutExercise;
import center.myfit.mapper.WorkoutExerciseMapper;
import center.myfit.mapper.WorkoutMapper;

import center.myfit.repository.WorkoutExerciseRepository;
import center.myfit.repository.WorkoutRepository;
import center.myfit.starter.dto.WorkoutDto;
import center.myfit.starter.service.UserAware;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
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
    Workout workout = workoutRepository.findByIdAndOwner(id, user)
            .orElseThrow(() -> new RuntimeException("Тренировка не найдена или "
                    + "не принадлежит пользователю"));
    log.info("треню нашли        {}", workout);

    log.info("Обновляем заголовок и описание  тренировки");
    workout.setTitle(dto.title());
    workout.setDescription(dto.description());
    log.info("название и описание обновили{}", workout);



    log.info("Удаляем старые упражнения из тренировки из базы");
    workoutExerciseRepository.deleteAllByWorkout(workout);

    log.info("Создаём новый лист упражнения запрос далее будет (Hibernate: = select) из-за маппера");
    List<WorkoutExercise> workoutExercises = workoutExerciseMapper.map(dto, workout);
    log.info("лист тренировок создан в рантайме для  workout entity {}", workoutExercises.toString());



    log.info("Сохраняем обновлённую тренировку");
    Workout updatedWorkout = workoutRepository.saveAndFlush(workout);

    log.info("сетим в базу новые workoutExercises");
    workoutExerciseRepository.saveAllAndFlush(workoutExercises);


    WorkoutDto workoutDto = workoutMapper.map(updatedWorkout, dto);
    log.info("        workoutDto такой      {}", workoutDto);
    return workoutDto;
  }

}





