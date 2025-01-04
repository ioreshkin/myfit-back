package center.myfit.controller;

import center.myfit.dto.ProgramDto;
import center.myfit.service.ProgramService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Контроллер тренировочных программ. */
@RestController
@RequestMapping("${api-prefix}/program")
@Slf4j
@RequiredArgsConstructor
public class ProgramController {
  private final ProgramService programService;

  /** Создание программы. */
  @PostMapping
  public ProgramDto createProgram(@RequestBody ProgramDto dto) {
    return programService.create(dto);
  }

  /** Получить все программы. */
  @GetMapping
  public List<ProgramDto> getAll() {
    return programService.getAll();
  }
}
