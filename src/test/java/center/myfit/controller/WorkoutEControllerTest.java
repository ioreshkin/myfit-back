package center.myfit.controller;

import static center.myfit.config.utils.TestConstants.*;

import center.myfit.BaseWebIntegrationTest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

@Transactional
public class WorkoutEControllerTest extends BaseWebIntegrationTest {
  private static final String BASE_URL = API_PREFIX + "/workout";
  @Autowired

  public WorkoutEControllerTest(WebApplicationContext context) {
    super(context);
  }

//todo  ЭТО ПОКА НЕ ДЕЛАЕМ ЭТО НА ПОЛУЧЕНИЕ ВСЕХ ТРЕНИРОВОК
//  @Test
//  @WithMockUser(keycloakId = "keyclId")
//  @Sql(
//      scripts = {"/sql/test_user.sql", "/sql/test_workouts.sql"},
//      executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//  void getAllWorkouts_shouldReturnAllWorkouts() throws Exception {
//    mockMvc
//        .perform(get(BASE_URL).contentType(CONTENT_TYPE_JSON))
//        .andExpect(status().isOk())
//        .andExpect(jsonPath("$", hasSize(2)));
//  }
}
