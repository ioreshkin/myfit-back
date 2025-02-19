package center.myfit.controller;

import center.myfit.BaseWebIntegrationTest;
import center.myfit.config.TuzProperties;
import center.myfit.starter.test.WithMockUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static center.myfit.config.utils.ResourcePool.create_workout;
import static center.myfit.config.utils.TestConstants.API_PREFIX;
import static center.myfit.config.utils.TestConstants.CONTENT_TYPE_JSON;
import static center.myfit.starter.test.AbstractTestResourcePool.getString;
import static org.hamcrest.Matchers.notNullValue;
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
  @Autowired private MockMvc mockMvc;
  @Autowired private TuzProperties properties;

    @Test
    @WithMockUser(keycloakId = "keyclId")
    @Sql(scripts = {"/sql/test_user.sql", "/sql/test_exercises.sql"})
    void createWorkout_shouldReturnCreatedWorkout() throws Exception {
      mockMvc
          .perform(
              post(BASE_URL)
                  .with(httpBasic(properties.getUsername(), properties.getPassword()))
                  .contentType(CONTENT_TYPE_JSON).content(getString(create_workout)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id", notNullValue()));
    }
}
