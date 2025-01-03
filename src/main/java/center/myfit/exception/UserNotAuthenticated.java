package center.myfit.exception;

/** Исключение UserNotAuthenticated. */
public class UserNotAuthenticated extends RuntimeException {
  /** Конструктор. */
  public UserNotAuthenticated(String message) {
    super(message);
  }
}
