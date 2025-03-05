package center.myfit.mapper;

import center.myfit.entity.Exercise;
import center.myfit.entity.Workout;
import center.myfit.entity.WorkoutExercise;
import center.myfit.repository.ExerciseRepository;
import center.myfit.repository.WorkoutExerciseRepository;
import center.myfit.starter.dto.ImageDto;
import center.myfit.starter.dto.WorkoutDto;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Маппер для преобразования WorkoutDto в список WorkoutExercise.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class WorkoutExerciseMapper {

  private final ExerciseRepository exerciseRepository;
  private final WorkoutExerciseRepository workoutExerciseRepository;

  /**
   * Маппер для WorkoutExercise.
  */
  public List<WorkoutExercise> map(WorkoutDto dto, Workout workout) {
    int[] k = new int[]{1};
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
                        .map(iterationDto -> {
                          WorkoutExercise workoutExercise = new WorkoutExercise();
                          workoutExercise.setRepeats(iterationDto.repeats());
                          workoutExercise.setWeight(iterationDto.weight());
                          workoutExercise.setOrders(k[0]++);
                          workoutExercise.setWorkout(workout);
                          workoutExercise.setExercise(exercise);
                          return workoutExercise;
                        });
              }).toList();
    return list;
  }

  /** Маппер для преобразования Workout в WorkoutDto. */
  public WorkoutDto map(Workout workout) {
    List<WorkoutDto.ExerciseWorkoutDto> exerciseWorkoutDtos = new ArrayList<>();
    AtomicInteger orderCounter = new AtomicInteger(1); // Счётчик order для упражнений

    workoutExerciseRepository.findByWorkout(workout)
                .stream()
                .collect(Collectors.groupingBy(we -> we.getExercise().getId()))
                .forEach((exerciseId, workoutExercises) -> {
                  List<WorkoutDto.IterationDto> iterations = workoutExercises.stream()
                            .map(we -> new WorkoutDto.IterationDto(we.getRepeats(), we.getWeight()))
                            .toList();
                  exerciseWorkoutDtos.add(new WorkoutDto.ExerciseWorkoutDto(
                            exerciseId,
                            orderCounter.getAndIncrement(),
                            iterations
                    ));
                });

    return new WorkoutDto(
                workout.getId(),
                workout.getOwner().getKeycloakId(),
                workout.getTitle(),
                exerciseWorkoutDtos,
                workout.getDescription(),
                workout.getImage() != null
                        ? new ImageDto(
                        workout.getImage().getOriginal(),
                        workout.getImage().getMobile(),
                        workout.getImage().getDesktop())
                        : null
    );
  }
}


