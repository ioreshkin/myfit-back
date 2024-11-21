package center.myfit.controller;

import center.myfit.BaseIntegrationTest;
import center.myfit.config.utils.WithMockUser;
import center.myfit.dto.ExerciseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

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

    @ParameterizedTest(name = "{0}")
    @MethodSource("badExerciseDto")
    @WithMockUser(email = TEST_EMAIL)
    @Sql(value = "/test_user.sql")
    void createExercise_shouldReturnBadRequest(String name, String value) throws Exception {

        mockMvc.perform(post(BASE_URL).contentType(CONTENT_TYPE_JSON).content(value)).andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> badExerciseDto() throws JsonProcessingException {
        ExerciseDto bad1 = new ExerciseDto(null, null, null, null, null);
        ExerciseDto bad2 = new ExerciseDto(null, "", "some desc", null, null);
        ExerciseDto bad3 = new ExerciseDto(null, "title", "", null, null);
        ExerciseDto bad4 = new ExerciseDto(null, "abcdefghijklmnopqrstvuwxyzabcdefghijklmnopqrstvuwxyzabcdefghijklmnopqrstvuwxyz", "some desc", null, null);


        return Stream.of(
                Arguments.of("null", mapper.writeValueAsString(bad1)),
                Arguments.of("emptyTitle", mapper.writeValueAsString(bad2)),
                Arguments.of("emptyDescription", mapper.writeValueAsString(bad3)),
                Arguments.of("LargeTitle", mapper.writeValueAsString(bad4))
        );
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