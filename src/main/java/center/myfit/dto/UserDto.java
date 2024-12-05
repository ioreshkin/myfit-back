package center.myfit.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserDto(
    @NotBlank @Length(max = 32) String firstname,
    @NotBlank @Length(max = 32) String lastname,
    @NotBlank @Length(max = 32) String role,
    String email,
    String keykloackId,
    Integer invite) {}
