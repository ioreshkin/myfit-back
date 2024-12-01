package center.myfit.mapper;

import center.myfit.dto.ExerciseDto;
import center.myfit.entity.Exercise;
import org.mapstruct.Mapper;

@Mapper
public interface ExerciseMapper {
    ExerciseDto map(Exercise exercise);

    Exercise map(ExerciseDto exercise);
}
