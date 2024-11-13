package center.myfit.service;

import center.myfit.dto.ProgramDto;
import center.myfit.dto.WorkoutForProgramDto;
import center.myfit.entity.Program;
import center.myfit.entity.ProgramWorkout;
import center.myfit.entity.User;
import center.myfit.entity.Workout;
import center.myfit.repository.ProgramRepository;
import center.myfit.repository.ProgramWorkoutRepository;
import center.myfit.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramService {
    private final ProgramRepository programRepository;
    private final WorkoutRepository workoutRepository;
    private final ProgramWorkoutRepository programWorkoutRepository;
    private final UserAware userAware;


    public ProgramDto create(ProgramDto dto) {
        Program program = map(dto);
        Program saved = programRepository.save(program);
        User owner = userAware.getUser();
        List<ProgramWorkout> programWorkouts = dto.workouts().stream().map(it -> map(it, saved, owner)).toList();

        programWorkoutRepository.saveAll(programWorkouts);

        return map(saved);
    }

    public List<ProgramDto> getAll() {
        User user = userAware.getUser();
        List<Workout> workouts = workoutRepository.findAllByOwner(user);
        return programWorkoutRepository.findAllByWorkout(workouts).stream().map(this::map).toList();
    }

    private Program map(ProgramDto dto) {
        return new Program() {{
            setTitle(dto.title());
            setDescription(dto.description());
        }};
    }

    private ProgramDto map(Program program) {
        return new ProgramDto(program.getId(), program.getTitle(), program.getDescription(), null);
    }

    private ProgramWorkout map(WorkoutForProgramDto dto, Program program, User owner) {
        Workout workout = workoutRepository.findByIdAndOwner(dto.id(), owner)
                .orElseThrow(() -> new RuntimeException("Workout with id = " + dto.id() + " not found"));

        return new ProgramWorkout() {{
            setProgram(program);
            setWorkout(workout);
            setOrderNumber(dto.orderNumber());
        }};
    }
}
