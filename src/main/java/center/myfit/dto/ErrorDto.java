package center.myfit.dto;

import java.time.LocalDateTime;

public record ErrorDto(
        String message,
        int code,
        LocalDateTime timestamp
) {
}
