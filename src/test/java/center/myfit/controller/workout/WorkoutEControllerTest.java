package center.myfit.controller.workout;

import static center.myfit.config.utils.TestConstants.API_PREFIX;
import static center.myfit.config.utils.TestConstants.CONTENT_TYPE_JSON;
import static center.myfit.config.utils.ResourcePool.expected_workout_list;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import center.myfit.BaseWebIntegrationTest;
import center.myfit.starter.test.WithMockUser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class WorkoutEControllerTest extends BaseWebIntegrationTest {
  private static final String BASE_URL = API_PREFIX + "/workout";
  private final ObjectMapper objectMapper = new ObjectMapper();

  public WorkoutEControllerTest(WebApplicationContext context) {
    super(context);
  }

  @Test
  @WithMockUser(keycloakId = "keyclId")
  @Sql(scripts = {"/sql/test_user.sql", "/sql/test_workouts.sql", "/sql/test_workout_exercises.sql", "/sql/test_workout_images.sql"},
          executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  void getAllWorkouts_shouldReturnAllWorkouts() throws Exception {
    MvcResult result = mockMvc.perform(get(BASE_URL).contentType(CONTENT_TYPE_JSON))
            .andExpect(status().isOk())
            .andReturn();

    String jsonResponse = new String(result.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
    List<Map<String, Object>> actualWorkouts = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

    String expectedJson = new String(Files.readAllBytes(Paths.get(expected_workout_list.getURI())), StandardCharsets.UTF_8);
    List<Map<String, Object>> expectedWorkouts = objectMapper.readValue(expectedJson, new TypeReference<>() {});

    assertThat(actualWorkouts).usingRecursiveComparison().isEqualTo(expectedWorkouts);
  }
}
