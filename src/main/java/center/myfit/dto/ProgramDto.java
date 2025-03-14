package center.myfit.dto;

import java.util.List;

/** ДТО программы тренировки. */
public record ProgramDto(
    Long id, String title, String description, List<WorkoutForProgramDto> workouts) {}
