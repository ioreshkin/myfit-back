package center.myfit.controller;

import center.myfit.dto.UserWorkoutExerciseDto;
import center.myfit.service.ExerciseService;
import center.myfit.starter.dto.ExerciseDto;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/** Контроллер упражнений. */
@RestController
@RequestMapping("${api-prefix}/exercise")
@Slf4j
@RequiredArgsConstructor
public class ExerciseController {
  private final ExerciseService exerciseService;

  /** Создать упражнение. */
  @PostMapping
  @PreAuthorize("hasRole('TUZ')")
  public ExerciseDto createExercise(@RequestBody @Valid ExerciseDto dto) {
    return exerciseService.create(dto);
  }

  /** Получить все упражнения. */
  @GetMapping
  public List<ExerciseDto> getAll() {
    return exerciseService.getAll();
  }

  /** Сохранить выполнение упражнения. */
  @PostMapping("/approach")
  public void saveApproach(@RequestBody @Valid UserWorkoutExerciseDto dto) {
    exerciseService.saveApproach(dto);
  }

  /** Обновление упражнения. */
  @PutMapping
  @PreAuthorize("hasRole('TUZ')")
  public ResponseEntity<ExerciseDto> updateExercise(@Valid @RequestBody ExerciseDto exerciseDto) {
    ExerciseDto updatedExercise = exerciseService.updateExercise(exerciseDto);
    return ResponseEntity.ok(updatedExercise);
  }
}
