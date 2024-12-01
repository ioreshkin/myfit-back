package center.myfit.mapper;

import center.myfit.dto.ProgramDto;
import center.myfit.entity.Program;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProgramMapper {
    ProgramDto map(Program program);

    Program map(ProgramDto program);
}
