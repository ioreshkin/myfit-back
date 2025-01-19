package center.myfit.service;

import center.myfit.entity.User;
import center.myfit.repository.UserRepository;
import center.myfit.starter.exception.UnauthorizedException;
import center.myfit.starter.service.UserAware;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Компонент для работы с контекстом безопасности. */
@Component
@RequiredArgsConstructor
public class UserAwareImpl implements UserAware<User> {

  private final UserRepository userRepository;

  /** Получение пользователя из контекста безопасности. */
  @Override
  public User getUser() {
    return userRepository
        .findUserByKeycloakId(getKeycloakId())
        .orElseThrow(() -> new UnauthorizedException("Пользователь не найден!"));
  }
}
