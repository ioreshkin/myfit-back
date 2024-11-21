package center.myfit.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record ExerciseDto(
        Long id,
        @NotBlank @Length(max = 64)
        String title,
        @NotBlank
        String description,
        String pictureUrl,
        String videoUrl
) {}
