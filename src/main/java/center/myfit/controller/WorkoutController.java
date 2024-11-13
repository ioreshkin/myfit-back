package center.myfit.controller;

import center.myfit.dto.WorkoutDto;
import center.myfit.entity.Workout;
import center.myfit.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workout")
@Slf4j
@RequiredArgsConstructor
public class WorkoutController {
    private final WorkoutService workoutService;

    @PostMapping
    public WorkoutDto createWorkout(@RequestBody WorkoutDto dto) {
        return workoutService.create(dto);
    }

    @GetMapping
    public List<WorkoutDto> getAll() {
        return workoutService.getAll();
    }
}
