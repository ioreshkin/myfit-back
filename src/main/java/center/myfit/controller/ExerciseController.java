package center.myfit.controller;

import center.myfit.dto.ExerciseDto;
import center.myfit.dto.UserWorkoutExerciseDto;
import center.myfit.service.ExerciseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercise")
@Slf4j
@RequiredArgsConstructor
public class ExerciseController {
    private final ExerciseService exerciseService;

    @PostMapping
    public ExerciseDto createExercise(@RequestBody @Valid ExerciseDto dto) {
        return exerciseService.create(dto);
    }

    @GetMapping
    public List<ExerciseDto> getAll() {
        return exerciseService.getAll();
    }

    @PostMapping("/approach")
    public void saveApproach(@RequestBody @Valid UserWorkoutExerciseDto dto) {
        exerciseService.saveApproach(dto);
    }
}
