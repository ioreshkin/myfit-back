package center.myfit.dto;

public record WorkoutDto(
        Long id,
        String title,
        String description,
        Integer difficulty
) {}
