package center.myfit.controller;

import center.myfit.dto.ExerciseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/program")
@Slf4j
@RequiredArgsConstructor
public class ProgramController {

    @PostMapping
    public ExerciseDto createExercise(@RequestBody ExerciseDto dto) {
        return exerciseService.createExercise(dto);
    }
}
