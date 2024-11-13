package center.myfit.dto;

import java.util.List;

public record WorkoutDto(
        Long id,
        String title,
        String description,
        Integer difficulty,
        List<ExerciseForWorkoutDto> exercises
) {}
