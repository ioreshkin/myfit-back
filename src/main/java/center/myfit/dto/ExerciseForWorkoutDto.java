package center.myfit.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

public record ExerciseForWorkoutDto(
    Long id,
    @NotNull @Max(1000) Integer repeats,
    @NotNull @Max(100) Integer sets,
    @NotNull @Max(32) Integer orderNumber) {}
