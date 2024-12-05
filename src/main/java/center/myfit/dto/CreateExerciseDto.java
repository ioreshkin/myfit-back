package center.myfit.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CreateExerciseDto(
    Long id,
    @NotBlank @Length(max = 64) String title,
    @NotBlank String description,
    ImageDto image,
    String videoUrl) {}
