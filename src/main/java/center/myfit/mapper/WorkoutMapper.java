package center.myfit.mapper;

import center.myfit.entity.User;
import center.myfit.entity.Workout;
import center.myfit.starter.dto.WorkoutDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** Маппер для Workout. */
@Mapper
public interface WorkoutMapper {

  /** Получение WorkoutDto. */
  //TODO пока это просто тупо сетит ID сохраненного в бд воркаута
  @Mapping(target = "id", source = "workout.id")
  @Mapping(target = "keycloakId", source = "dto.keycloakId")
  @Mapping(target = "title", source = "dto.title")
  @Mapping(target = "exercises", source = "dto.exercises")
  @Mapping(target = "description", source = "dto.description")
  @Mapping(target = "image", source = "dto.image")
  WorkoutDto map(Workout workout, WorkoutDto dto);

  /** Получение Workout. */
  @Mapping(target = "id", source = "workout.id")
  @Mapping(target = "owner", source = "owner")
  @Mapping(target = "image.original", source = "workout.image.original")
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "image.desktop", ignore = true)
  //TODO :а как потом эти поля досохранятся при апдейте ?
  // другой мапер написать или использовать
  // @Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
  // а кто запрашавает гет алл
  @Mapping(target = "image.mobile", ignore = true)
  Workout map(WorkoutDto workout, User owner);

}

