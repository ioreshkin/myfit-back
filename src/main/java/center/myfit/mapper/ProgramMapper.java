package center.myfit.mapper;

import center.myfit.dto.ProgramDto;
import center.myfit.entity.Program;
import org.mapstruct.Mapper;

/** Маппер для Program. */
@Mapper
public interface ProgramMapper {

  /** Получение ProgramDto. */
  ProgramDto map(Program program);

  /** Получение Program. */
  Program map(ProgramDto program);
}
