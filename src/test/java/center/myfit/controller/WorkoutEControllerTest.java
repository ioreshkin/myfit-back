package center.myfit.controller;

import static center.myfit.config.utils.TestConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import center.myfit.BaseWebIntegrationTest;
import center.myfit.dto.ExerciseWorkoutDto;
import center.myfit.dto.WorkoutDto;
import center.myfit.starter.test.WithMockUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.context.WebApplicationContext;

@Transactional
public class WorkoutEControllerTest extends BaseWebIntegrationTest {
  private static final String BASE_URL = API_PREFIX + "/workout";
  private static final ObjectMapper mapper = new ObjectMapper();

  public WorkoutEControllerTest(WebApplicationContext context) {
    super(context);
  }

  @Test
  @WithMockUser(keycloakId = "keyclId")
  @Sql(
      scripts = {"/sql/test_user.sql", "/sql/test_workouts.sql"},
      executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  void getAllWorkouts_shouldReturnAllWorkouts() throws Exception {
    mockMvc
        .perform(get(BASE_URL).contentType(CONTENT_TYPE_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  @WithMockUser(keycloakId = "keyclId")
  @Sql(scripts = {"/sql/test_user.sql", "/sql/test_exercises.sql"})
  void createWorkout_shouldReturnCreatedWorkout() throws Exception {
    mockMvc
        .perform(post(BASE_URL).contentType(CONTENT_TYPE_JSON).content(getWorkoutDtoJson()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", notNullValue()));
  }

  @Test
  @WithMockUser(keycloakId = "keyclId")
  @Sql(value = "/sql/test_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  void createWorkout_withWrongUser_shouldReturnUserNotFound() throws Exception {
    mockMvc
        .perform(post(BASE_URL).contentType(CONTENT_TYPE_JSON).content(getWorkoutDtoJson()))
        .andExpect(status().is5xxServerError());
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource({"badWorkoutDto", "badExerciseForWorkoutDto"})
  @WithMockUser(keycloakId = "keyclId")
  @Sql(value = "/sql/test_user.sql")
  void createWorkout_shouldReturnBadRequest(String name, String value) throws Exception {
    mockMvc
        .perform(post(BASE_URL).contentType(CONTENT_TYPE_JSON).content(value))
        .andExpect(status().isBadRequest());
  }

  private static Stream<Arguments> badWorkoutDto() throws JsonProcessingException {
    List<ExerciseWorkoutDto> dtos = new ArrayList<>();
    dtos.add(new ExerciseWorkoutDto(1L, 10, 5, 1));

    WorkoutDto bad1 = new WorkoutDto(null, null, null, null, null);
    WorkoutDto bad2 = new WorkoutDto(null, "", "some desc", 1, dtos);
    WorkoutDto bad3 = new WorkoutDto(null, "title", "", 1, dtos);
    WorkoutDto bad4 = new WorkoutDto(null, "title", "some desc", null, dtos);
    WorkoutDto bad5 = new WorkoutDto(null, "title", "some desc", 1, null);
    WorkoutDto bad6 =
        new WorkoutDto(
            null,
            "abcdefghijklmnopqrstvuwxyzabcdefghijklmnopqrstvuwxyzabcdefghijklmnopqrstvuwxyz",
            "some desc",
            1,
            dtos);

    return Stream.of(
        Arguments.of("null", mapper.writeValueAsString(bad1)),
        Arguments.of("emptyTitle", mapper.writeValueAsString(bad2)),
        Arguments.of("emptyDescription", mapper.writeValueAsString(bad3)),
        Arguments.of("emptyDifficulty", mapper.writeValueAsString(bad4)),
        Arguments.of("emptyDto", mapper.writeValueAsString(bad5)),
        Arguments.of("LargeTitle", mapper.writeValueAsString(bad6)));
  }

  private static Stream<Arguments> badExerciseForWorkoutDto() throws JsonProcessingException {
    List<ExerciseWorkoutDto> dtos = new ArrayList<>();
    dtos.add(new ExerciseWorkoutDto(1L, 10, 5, 1));

    WorkoutDto bad1 = new WorkoutDto(null, "title", "some desc", 1, null);
    WorkoutDto bad2 =
        new WorkoutDto(
            null, "title", "some desc", 1, List.of(new ExerciseWorkoutDto(null, null, 10, 1)));
    WorkoutDto bad3 =
        new WorkoutDto(
            null, "title", "some desc", 1, List.of(new ExerciseWorkoutDto(null, 5, null, 1)));
    WorkoutDto bad4 =
        new WorkoutDto(
            null, "title", "some desc", 1, List.of(new ExerciseWorkoutDto(null, 5, 10, null)));
    WorkoutDto bad5 =
        new WorkoutDto(
            null, "title", "some desc", 1, List.of(new ExerciseWorkoutDto(null, 1001, 10, 1)));
    WorkoutDto bad6 =
        new WorkoutDto(
            null, "title", "some desc", 1, List.of(new ExerciseWorkoutDto(null, 5, 101, 1)));
    WorkoutDto bad7 =
        new WorkoutDto(
            null, "title", "some desc", 1, List.of(new ExerciseWorkoutDto(null, 5, 10, 35)));

    return Stream.of(
        Arguments.of("null", mapper.writeValueAsString(bad1)),
        Arguments.of("emptyRepeats", mapper.writeValueAsString(bad2)),
        Arguments.of("emptySets", mapper.writeValueAsString(bad3)),
        Arguments.of("emptyOrderNumber", mapper.writeValueAsString(bad4)),
        Arguments.of("LargeRepeats", mapper.writeValueAsString(bad5)),
        Arguments.of("LargeSets", mapper.writeValueAsString(bad6)),
        Arguments.of("LargeOrderNumber", mapper.writeValueAsString(bad7)));
  }

  private WorkoutDto getDefaultWorkoutDto() {
    List<ExerciseWorkoutDto> dtos = new ArrayList<>();
    dtos.add(new ExerciseWorkoutDto(1L, 10, 5, 1));
    return new WorkoutDto(null, WORKOUT_TITLE, WORKOUT_DESCRIPTION, 2, dtos);
  }

  private String getWorkoutDtoJson() {
    try {
      return mapper.writeValueAsString(getDefaultWorkoutDto());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
