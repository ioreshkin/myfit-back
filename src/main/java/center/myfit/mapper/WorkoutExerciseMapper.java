package center.myfit.mapper;

import center.myfit.dto.ExerciseWorkoutDto;
import center.myfit.entity.Exercise;
import center.myfit.entity.Workout;
import center.myfit.entity.WorkoutExercise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** Маппер для WorkoutExercise. */
@Mapper
public interface WorkoutExerciseMapper {

  /** Получение WorkoutExercise. */
  @Mapping(target = "repeats", source = "dto.repeats")
  @Mapping(target = "sets", source = "dto.sets")
  @Mapping(target = "orderNumber", source = "dto.orderNumber")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  WorkoutExercise map(ExerciseWorkoutDto dto, Workout workout, Exercise exercise);
}
