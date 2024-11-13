package center.myfit.controller;

import center.myfit.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("coach")
@Slf4j
@RequiredArgsConstructor
public class CoachController {
    private final UserService userService;

    @PostMapping("follow/{code}")
    public void follow(@PathVariable("code") Integer code) {
        userService.followCoach(code);
    }
}
