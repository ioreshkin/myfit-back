package center.myfit.service;

import center.myfit.dto.ProgramDto;
import center.myfit.entity.Program;
import center.myfit.entity.ProgramWorkout;
import center.myfit.entity.User;
import center.myfit.entity.Workout;
import center.myfit.mapper.ProgramMapper;
import center.myfit.mapper.ProgramWorkoutMapper;
import center.myfit.repository.ProgramRepository;
import center.myfit.repository.ProgramWorkoutRepository;
import center.myfit.repository.WorkoutRepository;
import center.myfit.starter.service.UserAware;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Сервис работы с программами тренировок. */
@Service
@RequiredArgsConstructor
public class ProgramService {
  private final ProgramRepository programRepository;
  private final WorkoutRepository workoutRepository;
  private final ProgramWorkoutRepository programWorkoutRepository;
  private final UserAware<User> userAware;
  private final ProgramMapper programMapper;
  private final ProgramWorkoutMapper programWorkoutMapper;

  /** Создание программы тренировок. */
  public ProgramDto create(ProgramDto dto) {
    Program program = programMapper.map(dto);
    Program saved = programRepository.save(program);
    User owner = userAware.getUser();

    List<ProgramWorkout> programWorkouts =
        dto.workouts().stream()
            .map(
                exerciseForWorkoutDto -> {
                  ProgramWorkout programWorkout = new ProgramWorkout();
                  programWorkout.setProgram(program);
                  programWorkout.setOrderNumber(exerciseForWorkoutDto.orderNumber());
                  Workout workout =
                      workoutRepository
                          .findByIdAndOwner(exerciseForWorkoutDto.id(), owner)
                          .orElseThrow(
                              () ->
                                  new RuntimeException(
                                      "Workout with id = "
                                          + exerciseForWorkoutDto.id()
                                          + "not found"));
                  programWorkout.setWorkout(workout);
                  return programWorkout;
                })
            .toList();

    programWorkoutRepository.saveAll(programWorkouts);

    return programMapper.map(saved);
  }

  /** Получить все программы тренировок. */
  public List<ProgramDto> getAll() {
    User user = userAware.getUser();
    List<Workout> workouts = workoutRepository.findAllByOwner(user);
    return programWorkoutRepository.findAllByWorkoutIn(workouts).stream()
        .map(programMapper::map)
        .toList();
  }
}
