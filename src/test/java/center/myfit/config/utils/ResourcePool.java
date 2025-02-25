package center.myfit.config.utils;

import center.myfit.starter.test.AbstractTestResourcePool;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ResourcePool extends AbstractTestResourcePool {
    public static final Resource create_exercise =
            new ClassPathResource("json/exercise/create-exercise.json");
    public static final Resource expected_create_exercise =
            new ClassPathResource("json/exercise/expected-create-exercise.json");
    public static final Resource create_exercise_wrong_keycloak_id =
            new ClassPathResource("json/exercise/create-exercise-wrong_keycloak_id.json");
    public static final Resource empty_exercise =
            new ClassPathResource("json/exercise/empty-exercise.json");
    public static final Resource empty_title_exercise =
            new ClassPathResource("json/exercise/empty-title-exercise.json");
    public static final Resource empty_description_exercise =
            new ClassPathResource("json/exercise/empty-description-exercise.json");
    public static final Resource large_title_exercise =
            new ClassPathResource("json/exercise/large-title-exercise.json");

    public static final Resource create_workout =
            new ClassPathResource("json/workout/create-workout.json");
    public static final Resource create_workout_wrong_keycloak_id =
            new ClassPathResource("json/workout/create-workour-with-wrong_keycloak_id.json");
    public static final Resource empty_workout =
            new ClassPathResource("json/workout/empty-workout.json");
    public static final Resource empty_title_workout = new ClassPathResource("json/workout/empty-title-workout.json");
    public static final Resource null_title_workout =
            new ClassPathResource("json/workout/null-title-workout.json");
    public static final Resource short_title_workout =
            new ClassPathResource("json/workout/short-title-workout.json");
    public static final Resource long_title_workout =
            new ClassPathResource("json/workout/long-title-workout.json");
    public static final Resource null_exercises_workout =
            new ClassPathResource("json/workout/null-exercises-workout.json");
    public static final Resource empty_exercises_workout =
            new ClassPathResource("json/workout/empty-exercises-workout.json");
    public static final Resource empty_description_workout =
            new ClassPathResource("json/workout/empty-description-workout.json");
    public static final Resource null_description_workout =
            new ClassPathResource("json/workout/null-description-workout.json");
    public static final Resource spaces_title_workout =
            new ClassPathResource("json/workout/spaces-title-workout.json");
    public static final Resource spaces_description_workout =
            new ClassPathResource("json/workout/spaces-description-workout.json");

    public static final Resource null_exercise_id =
            new ClassPathResource("json/workout/exerciseWorkout/null-exercise-id.json");
    public static final Resource null_iterations =
            new ClassPathResource("json/workout/exerciseWorkout/null-iterations.json");
    public static final Resource empty_iterations =
            new ClassPathResource("json/workout/exerciseWorkout/empty-iterations.json");

    public static final Resource null_repeats =
            new ClassPathResource("json/workout/exerciseWorkout/iteration/null-repeats.json");
    public static final Resource negative_repeats =
            new ClassPathResource("json/workout/exerciseWorkout/iteration/negative-repeats.json");
    public static final Resource null_weight =
            new ClassPathResource("json/workout/exerciseWorkout/iteration/null-weight.json");
    public static final Resource negative_weight =
            new ClassPathResource("json/workout/exerciseWorkout/iteration/negative-weight.json");;
}
