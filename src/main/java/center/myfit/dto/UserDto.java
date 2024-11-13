package center.myfit.dto;

public record UserDto (
        String firstname,
        String lastname,
        String role,
        String email,
        String keykloackId,
        Integer invite
){}
