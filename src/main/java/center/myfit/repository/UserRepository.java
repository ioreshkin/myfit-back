package center.myfit.repository;

import center.myfit.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/** Репозиторий для User. */
public interface UserRepository extends JpaRepository<User, Long> {
  /**
   * Поиск пользователя по емейл.
   *
   * @param email - емейл
   * @return пользователь
   */
  Optional<User> findUserByEmail(String email);

  /**
   * Поиск пользователя по keycloakId.
   *
   * @param keycloakId keycloakId
   * @return пользователь
   */
  Optional<User> findUserByKeycloakId(String keycloakId);

  /**
   * Проверка существования пользователя по keycloakId.
   *
   * @param keycloakId keycloakId
   * @return true если пользователь найден
   */
  boolean existsByKeycloakId(String keycloakId);

  /**
   * Проверка существования пользователя по invite.
   *
   * @param invite invite
   * @return true если пользователь найден
   */
  boolean existsByInvite(Integer invite);

  /**
   * Поиск пользователя по invite.
   *
   * @param invite invite
   * @return пользователь
   */
  Optional<User> findByInvite(Integer invite);
}
