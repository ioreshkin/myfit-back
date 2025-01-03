package center.myfit.controller;

import center.myfit.dto.WorkoutDto;
import center.myfit.service.WorkoutService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Контроллер тренировок. */
@RestController
@RequestMapping("/workout")
@Slf4j
@RequiredArgsConstructor
public class WorkoutController {
  private final WorkoutService workoutService;

  /** Создание тренровки. */
  @PostMapping
  public WorkoutDto createWorkout(@RequestBody @Valid WorkoutDto dto) {
    return workoutService.create(dto);
  }

  /** Получить все трпенировки. */
  @GetMapping
  public List<WorkoutDto> getAll() {
    return workoutService.getAll();
  }
}
