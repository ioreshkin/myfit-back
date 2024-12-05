package center.myfit.controller;

import center.myfit.dto.CreateExerciseDto;
import center.myfit.dto.UserWorkoutExerciseDto;
import center.myfit.service.ExerciseService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exercise")
@Slf4j
@RequiredArgsConstructor
public class ExerciseController {
    private final ExerciseService exerciseService;

    @PostMapping
    public CreateExerciseDto createExercise(@RequestBody @Valid CreateExerciseDto dto) {
        return exerciseService.create(dto);
    }

    @GetMapping
    public List<CreateExerciseDto> getAll() {
        return exerciseService.getAll();
    }

    @PostMapping("/approach")
    public void saveApproach(@RequestBody @Valid UserWorkoutExerciseDto dto) {
        exerciseService.saveApproach(dto);
    }
}
