package center.myfit.dto;

public record ExerciseForWorkoutDto(
        Long id,
        Integer repeats,
        Integer sets,
        Integer orderNumber
) {}
