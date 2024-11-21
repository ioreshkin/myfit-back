package center.myfit.dto;

import java.time.LocalDateTime;

public record ExceptionDto(
        String message,
        LocalDateTime timestamp
) {}
