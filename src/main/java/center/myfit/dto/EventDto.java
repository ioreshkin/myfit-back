package center.myfit.dto;

public record EventDto(
        DetailsDto details,
        String type,
        String userId
) {}
