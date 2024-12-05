package center.myfit.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class KeycloakConfig {
  @Value("${keycloak.url}")
  private String url;

  @Value("${keycloak.username}")
  private String username;

  @Value("${keycloak.password}")
  private String password;

  @Value("${keycloak.realm}")
  private String realm;

  @Value("${keycloak.client-id}")
  private String clientId;

  @Bean
  Keycloak keycloak() {
    return KeycloakBuilder.builder()
        .grantType(OAuth2Constants.PASSWORD)
        .serverUrl(url)
        .realm(realm)
        .username(username)
        .password(password)
        .clientId(clientId)
        .build();
  }
}
