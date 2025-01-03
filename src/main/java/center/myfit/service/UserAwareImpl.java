package center.myfit.service;

import center.myfit.entity.User;
import center.myfit.exception.UserNotAuthenticated;
import center.myfit.repository.UserRepository;
import center.myfit.starter.service.UserAware;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/** Компонент для работы с контекстом безопасности. */
@Component
@RequiredArgsConstructor
public class UserAwareImpl implements UserAware<User> {

  private final UserRepository userRepository;

  /** Получение пользователя из контекста безопасности. */
  @Override
  public User getUser() {
    String keycloakId =
        Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
            .map(Authentication::getPrincipal)
            .filter(Jwt.class::isInstance)
            .map(Jwt.class::cast)
            .map(Jwt::getClaims)
            .map(it -> it.get(KEYCLOAK_ID_FIELD))
            .filter(Objects::nonNull)
            .map(String.class::cast)
            .filter(StringUtils::isNotBlank)
            .orElseThrow(() -> new UserNotAuthenticated("Нет токена!"));

    return userRepository
        .findUserByKeycloakId(keycloakId)
        .orElseThrow(() -> new UserNotAuthenticated("Пользователь не найден!"));
  }
}
