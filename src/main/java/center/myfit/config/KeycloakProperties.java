package center.myfit.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/** Настройки подключения к кейклоку. */
@Data
@Component
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {
  private String url;
  private String realm;
  private String username;
  private String password;
  private String clientId;
  private String queue;
}
