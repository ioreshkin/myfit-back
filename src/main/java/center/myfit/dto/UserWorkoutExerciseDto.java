package center.myfit.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UserWorkoutExerciseDto(
        @Min(1) @NotNull
        Integer repeats,
        @NotNull
        Long userId,
        @NotNull
        Long workoutExerciseId
) {
}
