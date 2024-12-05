package center.myfit.mapper;

import center.myfit.dto.CreateExerciseDto;
import center.myfit.entity.Exercise;
import org.mapstruct.Mapper;

@Mapper
public interface ExerciseMapper {
    CreateExerciseDto map(Exercise exercise);

    Exercise map(CreateExerciseDto exercise);
}
