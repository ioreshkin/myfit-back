package center.myfit.controller;

import static center.myfit.config.utils.ResourcePool.*;
import static center.myfit.config.utils.TestConstants.API_PREFIX;
import static center.myfit.starter.test.AbstractTestResourcePool.read;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import center.myfit.config.TuzProperties;
import center.myfit.entity.Exercise;
import center.myfit.repository.ExerciseRepository;
import jakarta.transaction.Transactional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class ExerciseControllerSecurityTest {

  private final String BASE_URL = API_PREFIX + "/exercise";

  @Autowired private MockMvc mockMvc;
  @Autowired private TuzProperties properties;

  @SpyBean private ExerciseRepository repository;

  @Captor private ArgumentCaptor<Exercise> captor;

  @Test
  @Sql(value = "/sql/test_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  void createExercise_shouldCreateExercise() throws Exception {
    Exercise expected = read(expected_create_exercise, Exercise.class);

    mockMvc
        .perform(
            post(BASE_URL)
                .with(httpBasic(properties.getUsername(), properties.getPassword()))
                .content(getString(create_exercise))
                .contentType(APPLICATION_JSON))
        .andExpect(status().isOk());

    verify(repository, times(1)).save(captor.capture());
    Exercise exercise = captor.getValue();

    assertNotNull(exercise);
    assertAll(
        () -> assertNotNull(exercise.getId()),
        () -> assertEquals(expected.getTitle(), exercise.getTitle()),
        () -> assertEquals(expected.getDescription(), exercise.getDescription()),
        () -> assertEquals(expected.getVideoUrl(), exercise.getVideoUrl()),
        () ->
            assertEquals(expected.getOwner().getKeycloakId(), exercise.getOwner().getKeycloakId()),
        () -> assertEquals(expected.getImage().getOriginal(), exercise.getImage().getOriginal()),
        () -> assertNull(exercise.getImage().getMobile()),
        () -> assertNull(exercise.getImage().getDesktop()));
  }

  @Test
  @Sql(value = "/sql/test_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  void createExercise_withWrongUser_shouldReturnForbidden() throws Exception {
    mockMvc
        .perform(
            post(BASE_URL)
                .with(httpBasic(properties.getUsername(), properties.getPassword()))
                .content(getString(create_exercise_wrong_keycloak_id))
                .contentType(APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  void createExercise_shouldReturnUnauthorized_whenWrongPassword() throws Exception {
    mockMvc
        .perform(
            post(BASE_URL)
                .with(httpBasic(properties.getUsername(), "wrong"))
                .content(getString(create_exercise))
                .contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("badExerciseDto")
  void createExercise_shouldReturnBadRequest(String name, String json) throws Exception {
    mockMvc
        .perform(
            post(BASE_URL)
                .with(httpBasic(properties.getUsername(), properties.getPassword()))
                .content(json)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  private static Stream<Arguments> badExerciseDto() {
    return Stream.of(
        Arguments.of("null", getString(empty_exercise)),
        Arguments.of("emptyTitle", getString(empty_title_exercise)),
        Arguments.of("emptyDescription", getString(empty_description_exercise)),
        Arguments.of("LargeTitle", getString(large_title_exercise)));
  }
}
