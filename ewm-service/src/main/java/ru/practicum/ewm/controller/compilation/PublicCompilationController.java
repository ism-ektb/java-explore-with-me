package ru.practicum.ewm.controller.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.service.compilation.CompilationService;


import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
@Validated

public class PublicCompilationController {
    private final CompilationService service;

    @GetMapping
    public List<CompilationDto> getCompilationsPublic(@RequestParam(required = false) boolean pinned,
                                                      @PositiveOrZero @RequestParam(defaultValue = "0", required = false) int from,
                                                      @Positive @RequestParam(defaultValue = "10", required = false) int size) {
               return service.getCompilationsPublic(pinned, PageRequest.of(from / size, size));
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilationByIdPublic(@PathVariable long compId) {
              return service.getCompilationByIdPublic(compId);
    }
}
