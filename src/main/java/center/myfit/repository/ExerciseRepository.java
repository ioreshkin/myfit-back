package center.myfit.repository;

import center.myfit.entity.Exercise;
import center.myfit.entity.User;
import java.util.List;
import java.util.Optional;
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

  /**
   * Поиск всех упражнений пользователя.
   *
   * @param owner пользователь * @param id идентификатор упражнения
   * @return список тренировок
   */
  Optional<Exercise> findByIdAndOwner(Long id, User owner);
}
