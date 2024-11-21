package center.myfit.mapper;

import center.myfit.dto.WorkoutDto;
import center.myfit.entity.Workout;
import org.mapstruct.Mapper;

@Mapper
public interface WorkoutMapper {
    WorkoutDto map(Workout workout);
    Workout map(WorkoutDto workout);
}
