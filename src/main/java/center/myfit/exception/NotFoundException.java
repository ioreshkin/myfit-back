package center.myfit.exception;

/** Исключение когда что-то не найдено. */
public class NotFoundException extends RuntimeException {
  /** Конструктор. */
  public NotFoundException(String message) {
    super(message);
  }
}
