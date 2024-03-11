package ru.practicum.ewm.controller.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.dto.compilation.UpdateCompilationDto;
import ru.practicum.ewm.service.compilation.CompilationService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationController {
    private final CompilationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto addByAdmin(@Valid @RequestBody NewCompilationDto dto) {
        return service.add(dto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByAdmin(@PathVariable long compId) {
        service.delete(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateByAdmin(@PathVariable long compId,
                                        @Valid @RequestBody UpdateCompilationDto dto) {
        return service.update(compId, dto);
    }
}
