package center.myfit.listener;

import center.myfit.dto.EventDto;
import center.myfit.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/** KeycloakListener. */
@Component
@Slf4j
@RequiredArgsConstructor
public class KeycloakListener {
  private final UserService userService;

  /** Обработка события кейклока. */
  @RabbitListener(queues = "${keycloak.queue}", concurrency = "2")
  public void eventHandler(EventDto dto) {
    log.info("Получено событие кейклока {}", dto.toString());
    userService.createUser(dto);
  }
}
