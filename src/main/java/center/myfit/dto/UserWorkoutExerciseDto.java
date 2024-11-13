package center.myfit.dto;

import center.myfit.entity.User;
import center.myfit.entity.WorkoutExercise;

public record UserWorkoutExerciseDto(
        Integer repeats,
        User user,
        WorkoutExercise workoutExercise
) {}
