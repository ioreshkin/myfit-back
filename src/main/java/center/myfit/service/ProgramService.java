package center.myfit.service;

import center.myfit.dto.ProgramDto;
import center.myfit.entity.Program;
import center.myfit.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProgramService {
    private final ProgramRepository programRepository;
    private final UserAware userAware;

    public ProgramDto createProgram(ProgramDto dto) {
        Program program = map(dto);
        Program saved = programRepository.save(program);
        return map(saved);
    }

    private Program map(ProgramDto dto) {
        return new Program() {{
            setTitle(dto.title());
            setDescription(dto.description());
        }};
    }

    private ProgramDto map(Program program) {
        return new ProgramDto(program.getId(), program.getTitle(), program.getDescription());
    }
}
