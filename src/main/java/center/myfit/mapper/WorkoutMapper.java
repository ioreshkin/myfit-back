package center.myfit.mapper;

import center.myfit.dto.WorkoutDto;
import center.myfit.entity.Workout;
import org.mapstruct.Mapper;

/** Маппер для Workout. */
@Mapper
public interface WorkoutMapper {

  /** Получение WorkoutDto. */
  WorkoutDto map(Workout workout);

  /** Получение Workout. */
  Workout map(WorkoutDto workout);
}
