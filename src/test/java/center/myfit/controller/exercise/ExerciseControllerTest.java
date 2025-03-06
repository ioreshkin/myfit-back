package center.myfit.controller.exercise;

import static center.myfit.config.utils.TestConstants.API_PREFIX;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import center.myfit.BaseWebIntegrationTest;
import center.myfit.starter.test.WithMockUser;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.context.WebApplicationContext;

@Transactional
class ExerciseControllerTest extends BaseWebIntegrationTest {
  private static final String BASE_URL = API_PREFIX + "/exercise";

  public ExerciseControllerTest(WebApplicationContext context) {
    super(context);
  }

  @Test
  @WithMockUser(keycloakId = "keyclId")
  @Sql(
      scripts = {"/sql/test_user.sql", "/sql/test_exercises.sql"},
      executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  void getAllExercise_shouldReturnTwoExercises() throws Exception {
    mockMvc
        .perform(get(BASE_URL).contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));
  }
}
