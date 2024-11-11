package center.myfit.dto;

public record ExerciseDto(
        Long id,
        String title,
        String description,
        String pictureUrl,
        String videoUrl
) {}
