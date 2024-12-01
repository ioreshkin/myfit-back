package center.myfit.mapper;

import center.myfit.dto.WorkoutForProgramDto;
import center.myfit.entity.Program;
import center.myfit.entity.ProgramWorkout;
import center.myfit.entity.Workout;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProgramWorkoutMapper {
    @Mapping(target = "orderNumber", source = "dto.orderNumber")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProgramWorkout map(WorkoutForProgramDto dto, Program program, Workout workout);
}


//Workout workout = workoutRepository.findByIdAndOwner(dto.id(), owner)
//        .orElseThrow(() -> new RuntimeException("Workout with id = " + dto.id() + " not found"));
//
//        return new ProgramWorkout() {{
//    setProgram(program);
//    setWorkout(workout);
//    setOrderNumber(dto.orderNumber());
//}};