package center.myfit.mapper;

import center.myfit.entity.Exercise;
import center.myfit.entity.User;
import center.myfit.starter.dto.ExerciseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** Маппер для Exercise. */
@Mapper
public interface ExerciseMapper {

  /** Получение ExerciseDto. */
  ExerciseDto map(Exercise exercise);

  /** Получение Exercise. */
  @Mapping(target = "id", source = "exercise.id")
  Exercise map(ExerciseDto exercise, User owner);
}
