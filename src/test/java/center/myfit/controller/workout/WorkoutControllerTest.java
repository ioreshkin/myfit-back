package center.myfit.controller.workout;

import static center.myfit.config.utils.ResourcePool.expected_workout_list;
import static center.myfit.config.utils.TestConstants.API_PREFIX;
import static center.myfit.config.utils.TestConstants.CONTENT_TYPE_JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import center.myfit.BaseWebIntegrationTest;
import center.myfit.config.utils.ResourcePool;
import center.myfit.starter.dto.WorkoutDto;
import center.myfit.starter.test.WithMockUser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

@Transactional
public class WorkoutControllerTest extends BaseWebIntegrationTest {
  private static final String BASE_URL = API_PREFIX + "/workout";
  private final ObjectMapper objectMapper = new ObjectMapper();

  public WorkoutControllerTest(WebApplicationContext context) {
    super(context);
  }

  @Test
  @WithMockUser(keycloakId = "keyclId")
  @Sql(
      scripts = {
        "/sql/test_user.sql",
        "/sql/test_workouts.sql",
        "/sql/test_workout_exercises.sql",
        "/sql/test_workout_images.sql"
      },
      executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  void getAllWorkouts_shouldReturnAllWorkouts() throws Exception {
    MvcResult result =
        mockMvc
            .perform(get(BASE_URL).contentType(CONTENT_TYPE_JSON))
            .andExpect(status().isOk()) .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andReturn();

    String jsonResponse = new String(result.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
    List<WorkoutDto> actualWorkouts =
        objectMapper.readValue(jsonResponse, new TypeReference<>() {});

    List<WorkoutDto> expectedWorkouts = ResourcePool.read(expected_workout_list, new TypeReference<>() {});

    assertThat(actualWorkouts).usingRecursiveComparison().isEqualTo(expectedWorkouts);
  }
}
