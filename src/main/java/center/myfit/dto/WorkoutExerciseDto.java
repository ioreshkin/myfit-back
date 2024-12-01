package center.myfit.dto;

public record WorkoutExerciseDto(
        Integer repeats,
        Integer sets,
        Integer orderNumber,
        Long exerciseId
) {
}
