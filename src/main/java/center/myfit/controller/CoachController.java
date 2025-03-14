package center.myfit.controller;

import center.myfit.dto.AssignProgramDto;
import center.myfit.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Контроллер тренера. */
@RestController
@RequestMapping("${api-prefix}/coach")
@Slf4j
@RequiredArgsConstructor
public class CoachController {
  private final UserService userService;

  /** Подписаться на тренера. */
  @PostMapping("follow/{code}")
  public void follow(@PathVariable("code") Integer code) {
    userService.followCoach(code);
  }

  /** Назначить тренировочную программу. */
  @PostMapping("assign-program")
  public void assignProgram(@RequestBody AssignProgramDto dto) {
    userService.assignProgram(dto);
  }
}
