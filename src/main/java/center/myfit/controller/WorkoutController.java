package center.myfit.controller;

import center.myfit.service.WorkoutService;
import center.myfit.starter.dto.WorkoutDto;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Контроллер тренировок. */
@RestController
@RequestMapping("${api-prefix}/workout")
@Slf4j
@RequiredArgsConstructor
public class WorkoutController {
  private final WorkoutService workoutService;

  /** Создание тренровки. */
  @PostMapping
  @PreAuthorize("hasRole('TUZ')")
  public WorkoutDto createWorkout(@RequestBody @Valid WorkoutDto dto) {
    log.info("Получен запрос на создание тренировки {}", dto.toString());
    return workoutService.createWorkout(dto);
  }

  /** Обновление тренировки. */
  @PutMapping("/{id}")
  @PreAuthorize("hasRole('TUZ')")
  public WorkoutDto updateWorkout(@PathVariable Long id, @RequestBody @Valid WorkoutDto dto) {
    log.info("СТЕП 1Получен запрос на обновление тренировки id={}, данные: {}", id, dto);
    return workoutService.updateWorkout(id, dto);
  }

  /** Получить все трпенировки. */
  @GetMapping
  public List<WorkoutDto> getAll() {
    return workoutService.getAll();
  }
}
