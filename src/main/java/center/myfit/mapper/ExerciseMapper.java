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

  /** Получение Exercise для создания нового. */
  @Mapping(target = "id", source = "exercise.id")
  @Mapping(target = "owner", source = "owner")
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "image.desktop", ignore = true)
  @Mapping(target = "image.mobile", ignore = true)
  Exercise mapToCreate(ExerciseDto exercise, User owner);
}
