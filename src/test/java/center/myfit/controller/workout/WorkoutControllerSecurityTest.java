package center.myfit.controller.workout;

import center.myfit.config.TuzProperties;
import center.myfit.config.utils.ResourcePool;
import center.myfit.repository.WorkoutRepository;
import center.myfit.starter.dto.WorkoutDto;
import com.fasterxml.jackson.core.type.TypeReference;
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
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static center.myfit.config.utils.ResourcePool.*;
import static center.myfit.config.utils.TestConstants.API_PREFIX;
import static center.myfit.config.utils.TestConstants.CONTENT_TYPE_JSON;
import static center.myfit.starter.test.AbstractTestResourcePool.getString;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class WorkoutControllerSecurityTest {

  private static final String BASE_URL = API_PREFIX + "/workout";
  private final ObjectMapper objectMapper = new ObjectMapper();


  @Autowired private MockMvc mockMvc;
  @Autowired private TuzProperties properties;

  private static Stream<Arguments> badWorkoutDto() {

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

  private static Stream<Arguments> badExerciseWorkoutForWorkoutDto() {
    return Stream.of(
        Arguments.of("nullExerciseId", getString(null_exercise_id)),
        Arguments.of("nullIterations", getString(null_iterations)),
        Arguments.of("emptyIterations", getString(empty_iterations)));
  }

  private static Stream<Arguments> badIterationForExerciseWorkoutDTO() {
    return Stream.of(
        Arguments.of("nullRepeats", getString(null_repeats)),
        Arguments.of("negativeRepeats", getString(negative_repeats)),
        Arguments.of("nullWeight", getString(null_weight)),
        Arguments.of("negativeWeight", getString(negative_weight)));
  }

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
  @Sql(
      scripts = {
        "/sql/forUpdate/test_user_forUpdate.sql",
        "/sql/forUpdate/test_workouts_forUpdate.sql",
        "/sql/forUpdate/test_workout_exercises_forUpdate.sql",
        "/sql/forUpdate/test_workout_images_forUpdate.sql"
      })
  void updateWorkout_shouldReturnUpdatedWorkout() throws Exception {
    String updateWorkoutJson = getString(update_workout);

    MvcResult result =
        mockMvc
            .perform(
                put(BASE_URL)
                    .with(httpBasic(properties.getUsername(), properties.getPassword()))
                    .content(updateWorkoutJson)
                    .contentType(CONTENT_TYPE_JSON))
            .andExpect(status().isOk())
            .andReturn();

    String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
    WorkoutDto actualWorkout = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
    WorkoutDto expectedWorkout =
        ResourcePool.read(expected_update_workout, new TypeReference<>() {});

    assertThat(actualWorkout).usingRecursiveComparison().isEqualTo(expectedWorkout);
  }

  @Test
  @Sql(value = "/sql/test_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  void createWorkout_withWrongUser_shouldReturnForbidden() throws Exception {
    mockMvc
        .perform(
            post(BASE_URL)
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
  @MethodSource({
    "badWorkoutDto",
    "badExerciseWorkoutForWorkoutDto",
    "badIterationForExerciseWorkoutDTO"
  })
  @Sql(value = "/sql/test_user.sql")
  void createWorkout_shouldReturnBadRequest(String name, String value) throws Exception {
    mockMvc
        .perform(
            post(BASE_URL)
                .with(httpBasic(properties.getUsername(), properties.getPassword()))
                .content(value)
                .contentType(CONTENT_TYPE_JSON))
        .andExpect(status().isBadRequest());
  }
}
