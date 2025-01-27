package center.myfit.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/** ДТО связи пользователя с упражнением тренировки. */
public record UserWorkoutExerciseDto(
    @Min(1) @NotNull Integer repeats,
    @NotNull String keycloakId,
    @NotNull Long workoutExerciseId) {}
