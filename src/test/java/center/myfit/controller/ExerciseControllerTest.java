package center.myfit.controller;

import center.myfit.BaseIntegrationTest;
import center.myfit.config.utils.WithMockUser;
import center.myfit.dto.ExerciseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.context.WebApplicationContext;

import static center.myfit.config.utils.TestConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class ExerciseControllerTest extends BaseIntegrationTest {
    private static final String BASE_URL = "/exercise";
    private static final ObjectMapper mapper = new ObjectMapper();

    public ExerciseControllerTest(WebApplicationContext context) {
        super(context);
    }

    @Test
    @WithMockUser(email = TEST_EMAIL)
    @Sql(scripts = {"/test_user.sql", "/test_exercises.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getAllExercise_shouldReturnAllExercises() throws Exception {
        mockMvc.perform(get(BASE_URL).contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @WithMockUser(email = TEST_EMAIL)
    @Sql(value = "/test_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void createExercise_shouldReturnCreatedExercise() throws Exception {

        mockMvc.perform(post(BASE_URL).contentType(CONTENT_TYPE_JSON)
                        .content(getExerciseDtoJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    @WithMockUser(email = WRONG_USER_EMAIL)
    @Sql(value = "/test_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void createExercise_withWrongUser_shouldReturnUserNotFound() throws Exception {

        mockMvc.perform(post(BASE_URL).contentType(CONTENT_TYPE_JSON)
                        .content(getExerciseDtoJson()))
                .andExpect(status().is5xxServerError());
    }

    private ExerciseDto getDefaultExerciseDto() {
        return new ExerciseDto(null, EXERCISE_TITLE, EXERCISE_DESCRIPTION, null, null);
    }

    private String getExerciseDtoJson() {
        try {
            return mapper.writeValueAsString(getDefaultExerciseDto());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}