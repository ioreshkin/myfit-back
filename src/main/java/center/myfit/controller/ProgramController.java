package center.myfit.controller;

import center.myfit.dto.ProgramDto;
import center.myfit.service.ProgramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/program")
@Slf4j
@RequiredArgsConstructor
public class ProgramController {
    private final ProgramService programService;

    @PostMapping
    public ProgramDto createProgram(@RequestBody ProgramDto dto) {
        return programService.create(dto);
    }

    @GetMapping
    public List<ProgramDto> getAll() {
        return programService.getAll();
    }
}
