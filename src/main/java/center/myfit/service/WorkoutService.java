package center.myfit.service;

import center.myfit.dto.ExerciseForWorkoutDto;
import center.myfit.dto.WorkoutDto;
import center.myfit.entity.Exercise;
import center.myfit.entity.User;
import center.myfit.entity.Workout;
import center.myfit.entity.WorkoutExercise;
import center.myfit.repository.ExerciseRepository;
import center.myfit.repository.WorkoutExerciseRepository;
import center.myfit.repository.WorkoutRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final UserAware userAware;

    @Transactional
    public WorkoutDto create(WorkoutDto dto) {
        Workout workout = map(dto);
        User user = userAware.getUser();
        workout.setOwner(user);
        Workout saved = workoutRepository.save(workout);
        List<WorkoutExercise> workoutExercises = dto.exercises().stream().map(it -> map(it, workout)).toList();
        workoutExerciseRepository.saveAll(workoutExercises);

        return map(saved);
    }

    public List<WorkoutDto> getAll() {
        User user = userAware.getUser();
        return workoutRepository.findAllByOwner(user).stream().map(this::map).toList();
    }

    private Workout map(WorkoutDto dto) {
        return new Workout() {{
            setTitle(dto.title());
            setDescription(dto.description());
            setDifficulty(dto.difficulty());
        }};
    }

    private WorkoutDto map(Workout workout) {
        return new WorkoutDto(workout.getId(), workout.getTitle(), workout.getDescription(), workout.getDifficulty(), null);
    }

    private WorkoutExercise map(ExerciseForWorkoutDto dto, Workout workout) {
        Exercise exercise = exerciseRepository.findById(dto.id())
                .orElseThrow(() -> new RuntimeException("Exercise with id = " + dto.id() + " not found"));

        return new WorkoutExercise() {{
            setWorkout(workout);
            setExercise(exercise);
            setRepeats(dto.repeats());
            setSets(dto.sets());
            setOrderNumber(dto.orderNumber());
        }};
    }
}
