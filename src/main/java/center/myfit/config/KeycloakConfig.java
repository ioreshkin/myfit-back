package center.myfit.config;

import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/** Настройки подключения к кейклоку. */
@Configuration
@EnableAsync
@RequiredArgsConstructor
public class KeycloakConfig {
  private final KeycloakProperties properties;

  /** Создание Keycloak. */
  @Bean
  Keycloak keycloak() {
    return KeycloakBuilder.builder()
        .grantType(OAuth2Constants.PASSWORD)
        .serverUrl(properties.getUrl())
        .realm(properties.getRealm())
        .username(properties.getUsername())
        .password(properties.getPassword())
        .clientId(properties.getClientId())
        .build();
  }
}
