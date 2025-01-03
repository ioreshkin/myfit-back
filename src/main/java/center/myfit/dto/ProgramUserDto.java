package center.myfit.dto;

/** ДТО связи пользователя с тренировочной программой. */
public record ProgramUserDto(Integer lastOrderNumber, ProgramDto program, UserDto user) {}
