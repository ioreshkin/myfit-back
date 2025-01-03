package center.myfit.repository;

import center.myfit.entity.User;
import center.myfit.entity.Workout;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/** Репозиторий для WorkoutRepository. */
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
  /**
   * Поиск тренировки по id и пользователю.
   *
   * @param id - id тренировки
   * @param owner - пользователь
   * @return тренировка
   */
  Optional<Workout> findByIdAndOwner(Long id, User owner);

  /**
   * Поиск всех тренировок пользователя.
   *
   * @param owner - пользователь
   * @return тренировка
   */
  List<Workout> findAllByOwner(User owner);
}
