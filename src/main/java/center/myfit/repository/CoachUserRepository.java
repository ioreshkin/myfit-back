package center.myfit.repository;

import center.myfit.entity.CoachUser;
import center.myfit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/** Репозиторий для CoachUser. */
public interface CoachUserRepository extends JpaRepository<CoachUser, Long> {

  /**
   * Поиск связи с тренером.
   *
   * @param coach - тренер
   * @param follower - подписчик
   * @return true если подписка существует
   */
  boolean existsByCoachAndFollower(User coach, User follower);
}
