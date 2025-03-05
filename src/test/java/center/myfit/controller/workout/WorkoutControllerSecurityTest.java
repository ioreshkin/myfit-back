package center.myfit.controller.workout;

import java.util.stream.Stream;
import center.myfit.config.TuzProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static center.myfit.config.utils.ResourcePool.*;
import static center.myfit.config.utils.TestConstants.*;
import static center.myfit.starter.test.AbstractTestResourcePool.getString;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class WorkoutControllerSecurityTest {

    private static final String BASE_URL = API_PREFIX + "/workout";
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TuzProperties properties;

    @Test
    @Sql(scripts = {"/sql/test_user.sql", "/sql/test_exercises.sql"})
    void createWorkout_shouldReturnCreatedWorkout() throws Exception {
        mockMvc
                .perform(
                        post(BASE_URL)
                                .with(httpBasic(properties.getUsername(), properties.getPassword()))
                                .content(getString(create_workout))
                                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()));
    }


    @Test
    @Sql(value = "/sql/test_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void createWorkout_withWrongUser_shouldReturnForbidden() throws Exception {
        mockMvc
                .perform(post(BASE_URL)
                        .with(httpBasic(properties.getUsername(), properties.getPassword()))
                        .content(getString(create_workout_wrong_keycloak_id))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void createWorkout_shouldReturnUnauthorized_whenWrongPassword() throws Exception {
        mockMvc
                .perform(
                        post(BASE_URL)
                                .with(httpBasic(properties.getUsername(), "wrong"))
                                .content(getString(create_workout))
                                .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource({"badWorkoutDto", "badExerciseWorkoutForWorkoutDto", "badIterationForExerciseWorkoutDTO"})
    @Sql(value = "/sql/test_user.sql")
    void createWorkout_shouldReturnBadRequest(String name, String value) throws Exception {
        mockMvc
                .perform(post(BASE_URL)
                        .with(httpBasic(properties.getUsername(), properties.getPassword()))
                        .content(value)
                        .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> badWorkoutDto() throws JsonProcessingException {

        return Stream.of(
                Arguments.of("null", getString(empty_workout)),
                Arguments.of("nullTitle", getString(null_title_workout)),
                Arguments.of("emptyTitle", getString(empty_title_workout)),
                Arguments.of("spacesTitle", getString(spaces_title_workout)), // Пробелы в title
                Arguments.of("shortTitle", getString(short_title_workout)),
                Arguments.of("longTitle", getString(long_title_workout)),
                Arguments.of("nullExercises", getString(null_exercises_workout)),
                Arguments.of("emptyExercises", getString(empty_exercises_workout)),
                Arguments.of("nullDescription", getString(null_description_workout)),
                Arguments.of("emptyDescription", getString(empty_description_workout)),
                Arguments.of("spacesDescription", getString(spaces_description_workout)));
    }

    private static Stream<Arguments> badExerciseWorkoutForWorkoutDto() throws JsonProcessingException {
        return Stream.of(
                Arguments.of("nullExerciseId", getString(null_exercise_id)),
                Arguments.of("nullIterations", getString(null_iterations)),
                Arguments.of("emptyIterations", getString(empty_iterations))
        );
    }

    private static Stream<Arguments> badIterationForExerciseWorkoutDTO() throws JsonProcessingException {
        return Stream.of(
                Arguments.of("nullRepeats", getString(null_repeats)),
                Arguments.of("negativeRepeats", getString(negative_repeats)),
                Arguments.of("nullWeight", getString(null_weight)),
                Arguments.of("negativeWeight", getString(negative_weight))
        );
    }
}
