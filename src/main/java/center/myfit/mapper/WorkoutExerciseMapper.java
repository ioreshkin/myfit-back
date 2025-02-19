package center.myfit.mapper;

import center.myfit.entity.Exercise;
import center.myfit.entity.Workout;
import center.myfit.entity.WorkoutExercise;
import center.myfit.repository.ExerciseRepository;
import center.myfit.starter.dto.WorkoutDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * Получение WorkoutExercise.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class WorkoutExerciseMapper {

  private final ExerciseRepository exerciseRepository;

  /**
   * Маппер для WorkoutExercise.
   */
  public List<WorkoutExercise> map(WorkoutDto dto, Workout workout) {
    log.info("начало маппинга из воркаутДТО и воркаутСохр в лист упраженний для сохранения в бд");

    int[] k = new int[] {1};
    List<WorkoutExercise> list = dto.exercises().stream()
        .flatMap(exerciseDto -> {
          Exercise exercise =
              exerciseRepository
                  .findById(exerciseDto.exerciseId())
                  .orElseThrow(
                      () ->
                          new RuntimeException(
                              "Exercise with id = "
                                  + exerciseDto.exerciseId()
                                  + "not found"));

          return exerciseDto.iterations().stream()
              .map(iterationDto  -> {
                WorkoutExercise workoutExercise = new WorkoutExercise();
                workoutExercise.setRepeats(iterationDto .repeats());
                workoutExercise.setWeight(iterationDto .weight());
                workoutExercise.setOrders(k[0]++); // тут не верный маппинг
                workoutExercise.setWorkout(workout);
                workoutExercise.setExercise(exercise);
                return workoutExercise;
              });
        }).toList();
    return list;
  }

}

