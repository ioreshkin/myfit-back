package center.myfit.repository;

import center.myfit.entity.Exercise;
import center.myfit.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/** Репозиторий для Exercise. */
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

  /**
   * Поиск всех упражнений пользователя.
   *
   * @param owner пользователь
   * @return список тренировок
   */
  List<Exercise> findAllByOwner(User owner);
}
