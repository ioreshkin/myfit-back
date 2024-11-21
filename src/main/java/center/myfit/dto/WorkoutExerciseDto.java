package center.myfit.dto;

import center.myfit.entity.Exercise;
import center.myfit.entity.Workout;

public record WorkoutExerciseDto(
        Integer repeats,
        Integer sets,
        WorkoutDto workout,
        ExerciseDto exercise
) {}
