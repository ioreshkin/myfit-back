package center.myfit.dto;

import center.myfit.entity.Program;
import center.myfit.entity.User;

public record ProgramUserDto(
        Integer lastOrderNumber,
        Program program,
        User user
) {}
