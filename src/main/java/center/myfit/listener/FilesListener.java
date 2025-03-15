package center.myfit.listener;

import center.myfit.service.ExerciseService;
import center.myfit.service.WorkoutService;
import center.myfit.starter.dto.ExerciseImageDto;
import center.myfit.starter.dto.WorkoutImageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/** FilesListener. */
@Slf4j
@RequiredArgsConstructor
@Component
public class FilesListener {
  private final ExerciseService exerciseService;
  private final WorkoutService workoutService;

  /** Обработка события сжатия изображений упражнения. */
  @RabbitListener(queues = "${stage}" + "_files_back_exercise_image_q", concurrency = "2")
  public void handleExerciseImage(ExerciseImageDto dto) {
    log.info("Получено событие сжатия изображений упражнения {}", dto.toString());
    exerciseService.updateExerciseImage(dto);
    log.info("Ссылки на изображения успешно обновлены!");
  }

  /** Обработка события сжатия изображений тренировки. */
  @RabbitListener(queues = "${stage}" + "_files_back_workout_image_q", concurrency = "2")
  public void handleWorkoutImage(WorkoutImageDto dto) {
    log.info("Получено событие сжатия изображений тренировки {}", dto.toString());
    workoutService.updateWorkoutImage(dto);
    log.info("Ссылки на изображения успешно обновлены!");
  }
}
