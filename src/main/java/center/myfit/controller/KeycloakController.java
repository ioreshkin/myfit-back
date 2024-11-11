package center.myfit.controller;

import center.myfit.dto.EventDto;
import center.myfit.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class KeycloakController {
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('KEYCLOAK')")
    public void eventHandler(@RequestBody EventDto dto) {
        log.info(dto.toString());
        userService.createUser(dto);
    }
}
