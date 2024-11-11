package center.myfit.service;

import center.myfit.dto.EventDto;
import center.myfit.entity.User;
import center.myfit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final Keycloak keycloak;
    private final UserRepository userRepository;

    @Async
    public void createUser(EventDto dto) {
        if (dto.type().equals("LOGIN")) {
            return;
        }
        UserRepresentation representation = keycloak.realm("myfit").users().get(dto.userId()).toRepresentation();
        User user = new User();
        user.setUsername(representation.getUsername());
        user.setFirstName(representation.getFirstName());
        user.setLastName(representation.getLastName());
        user.setEmail(representation.getEmail());
        user.setKeycloakId(representation.getId());
        userRepository.save(user);
    }
}
