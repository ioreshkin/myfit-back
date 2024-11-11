package center.myfit.service;

import center.myfit.dto.WorkoutDto;
import center.myfit.entity.Workout;
import center.myfit.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkoutService {
    private final WorkoutRepository workoutRepository;

    public WorkoutDto createWorkout(WorkoutDto dto) {
        Workout workout = map(dto);
        Workout saved = workoutRepository.save(workout);
        return map(saved);
    }

    private Workout map(WorkoutDto dto) {
        return new Workout() {{
            setTitle(dto.title());
            setDescription(dto.description());
            setDifficulty(dto.difficulty());
        }};
    }

    private WorkoutDto map(Workout workout) {
        return new WorkoutDto(workout.getId(), workout.getTitle(), workout.getDescription(), workout.getDifficulty());
    }
}
