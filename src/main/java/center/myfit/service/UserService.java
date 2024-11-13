package center.myfit.service;

import center.myfit.dto.EventDto;
import center.myfit.dto.ProgramDto;
import center.myfit.dto.UserDto;
import center.myfit.entity.CoachUser;
import center.myfit.entity.Program;
import center.myfit.entity.ProgramUser;
import center.myfit.entity.User;
import center.myfit.repository.CoachUserRepository;
import center.myfit.repository.ProgramRepository;
import center.myfit.repository.ProgramUserRepository;
import center.myfit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class UserService {
    private final Keycloak keycloak;
    private final UserRepository userRepository;
    private final CoachUserRepository coachUserRepository;
    private final ProgramRepository programRepository;
    private final ProgramUserRepository programUserRepository;
    private final UserAware userAware;

    @Async
    public void createUser(EventDto dto) {
        if (dto.type().equals("LOGIN")) {
            return;
        }
        int invite;
        do {
            invite = ThreadLocalRandom.current().nextInt(100000, 9999999);
        } while (!userRepository.existsByInvite(invite));

        UserRepresentation representation = keycloak.realm("myfit").users().get(dto.userId()).toRepresentation();
        User user = new User();
        user.setFirstName(representation.getFirstName());
        user.setLastName(representation.getLastName());
        user.setEmail(representation.getEmail());
        user.setKeycloakId(representation.getId());
        user.setInvite(invite);

        userRepository.save(user);
    }

    public void followCoach(Integer invite) {
        User coach = userRepository.findByInvite(invite)
                .orElseThrow(() -> new RuntimeException("User with invite code = " + invite + " not found"));

        boolean isCoach = keycloak.realm("").users()
                .get(coach.getKeycloakId()).toRepresentation().getRealmRoles().stream().anyMatch("coach"::equals);

        if (!isCoach) {
            throw new RuntimeException("This user is not coach");
        }

        User user = userAware.getUser();

        if (!coachUserRepository.existsByCoachAndFollower(coach, user)) {
            throw new RuntimeException("User already follow this coach");
        }

        CoachUser coachUser = new CoachUser() {{
            setCoach(coach);
            setFollower(user);
        }};

        coachUserRepository.save(coachUser);
    }

    public void assignProgram(Long userId, Long programId) {
        User coach = userAware.getUser();
        boolean isCoach = keycloak.realm("").users()
                .get(coach.getKeycloakId()).toRepresentation().getRealmRoles().stream().anyMatch("coach"::equals);
        if (!isCoach) {
            throw new RuntimeException("User is not coach");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with id = " + userId + " not found"));
        if (!coachUserRepository.existsByCoachAndFollower(coach, user)) {
            throw new RuntimeException("User is not following this coach");
        }
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new RuntimeException("Program with id = " + programId + " not found"));
        ProgramUser programUser = new ProgramUser() {{
            setUser(user);
            setProgram(program);
        }};
        programUserRepository.save(programUser);
    }

}
