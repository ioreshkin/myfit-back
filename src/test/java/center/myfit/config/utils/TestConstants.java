package center.myfit.config.utils;

import center.myfit.starter.dto.ImageDto;

public final class TestConstants {
  public static final String API_PREFIX = "/api/back";
  public static final String CONTENT_TYPE_JSON = "application/json";
  public static final String WORKOUT_TITLE = "workout title";
  public static final String WORKOUT_DESCRIPTION = "workout description";
  public static final ImageDto IMAGE = new ImageDto("https://minio/original-image.jpg",
      null, null);
}

