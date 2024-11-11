package center.myfit.service;

import center.myfit.entity.User;
import center.myfit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAware {
    private final static String EMAIL_FIELD = "email";
    private final UserRepository userRepository;

    public User getUser() {
        String email = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(Jwt.class::isInstance)
                .map(Jwt.class::cast)
                .map(Jwt::getClaims)
                .map(it -> it.get(EMAIL_FIELD))
                .filter(Objects::nonNull)
                .map(String.class::cast)
                .filter(StringUtils::isNotBlank)
                .orElseThrow(() -> new RuntimeException("No token"));
        return userRepository.findUserByEmail(email).orElseThrow(() -> new RuntimeException("No user"));
    }
}
