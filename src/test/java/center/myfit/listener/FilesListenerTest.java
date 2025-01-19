package center.myfit.listener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import center.myfit.BaseIntegrationTest;
import center.myfit.entity.Exercise;
import center.myfit.exception.NotFoundException;
import center.myfit.starter.dto.ExerciseImageDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

@Transactional
class FilesListenerTest extends BaseIntegrationTest {
  @Autowired FilesListener filesListener;
  @Captor ArgumentCaptor<Exercise> captor;

  @Test
  @Sql(scripts = {"/sql/test_user.sql", "/sql/test_exercise_image.sql"})
  void handleExerciseImage_shouldRunSuccess() {
    ExerciseImageDto exerciseImageDto =
        new ExerciseImageDto(
            1L, new ExerciseImageDto.ImageDto("originalUrl", "mobileUrl", "desktopUrl"));
    filesListener.handleExerciseImage(exerciseImageDto);

    verify(exerciseRepository).save(captor.capture());

    Exercise exercise = captor.getValue();

    assertEquals(exerciseImageDto.image().mobile(), exercise.getImage().getMobile());
    assertEquals(exerciseImageDto.image().desktop(), exercise.getImage().getDesktop());
    assertEquals(exerciseImageDto.image().original(), exercise.getImage().getOriginal());
  }

  @Test
  void handleExerciseImage_shouldThrow_whenExerciseNotFound() {
    ExerciseImageDto exerciseImageDto =
        new ExerciseImageDto(
            1L, new ExerciseImageDto.ImageDto("originalUrl", "mobileUrl", "desktopUrl"));
    assertThrows(
        NotFoundException.class, () -> filesListener.handleExerciseImage(exerciseImageDto));
  }
}
