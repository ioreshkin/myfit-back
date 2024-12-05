package center.myfit.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.hibernate.validator.constraints.Length;

public record WorkoutDto(
    Long id,
    @NotBlank @Length(max = 64) String title,
    @NotBlank String description,
    @NotNull Integer difficulty,
    @NotNull @Valid List<ExerciseForWorkoutDto> exercises) {}
