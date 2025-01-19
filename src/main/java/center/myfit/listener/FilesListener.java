package center.myfit.listener;

import center.myfit.service.ExerciseService;
import center.myfit.starter.dto.ExerciseImageDto;
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

  /** Обработка события сжатия изображений упражнения. */
  @RabbitListener(queues = "${stage}" + "_files_back_exercise_image_q", concurrency = "2")
  public void handleExerciseImage(ExerciseImageDto dto) {
    log.info("Получено событие сжатия изображений упражнения {}", dto.toString());
    exerciseService.updateExerciseImage(dto);
    log.info("Ссылки на изображения успешно обновлены!");
  }
}
