package center.myfit.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

/** ДТО связи упражнения с тренировкой. */
public record ExerciseWorkoutDto(
    Long id,
    @NotNull @Max(1000) Integer repeats,
    @NotNull @Max(100) Integer sets,
    @NotNull @Max(32) Integer orderNumber) {}
