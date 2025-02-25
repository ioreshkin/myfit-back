package center.myfit.controller;


import center.myfit.service.WorkoutService;
import center.myfit.starter.dto.WorkoutDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
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
    log.info("пришел WorkoutDto для сохранения {}",
        dto.toString());
    return workoutService.create(dto);
  }

  //  /** Получить все трпенировки. */
  //  @GetMapping
  //  public List<WorkoutDto> getAll() {
  //    return workoutService.getAll();
  //  }
}
