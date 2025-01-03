package center.myfit.mapper;

import center.myfit.dto.WorkoutForProgramDto;
import center.myfit.entity.Program;
import center.myfit.entity.ProgramWorkout;
import center.myfit.entity.Workout;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** Маппер для ProgramWorkout. */
@Mapper
public interface ProgramWorkoutMapper {

  /** Получение ProgramWorkout. */
  @Mapping(target = "orderNumber", source = "dto.orderNumber")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  ProgramWorkout map(WorkoutForProgramDto dto, Program program, Workout workout);
}
