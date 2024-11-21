package center.myfit.dto;

import center.myfit.entity.Program;
import center.myfit.entity.Workout;

public record ProgramWorkoutDto(
        Integer orderNumber,
        ProgramDto program,
        WorkoutDto workout
) {}
