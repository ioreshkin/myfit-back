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
}
