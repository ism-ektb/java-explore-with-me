package ru.practicum.ewm.dto.compilation.patcher;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.dto.compilation.UpdateCompilationDto;
import ru.practicum.ewm.dto.event.mapper.EventSetMapper;
import ru.practicum.ewm.model.Compilation;

@Component
@RequiredArgsConstructor
public class CompilationPatch {

    @Autowired
    private final EventSetMapper mapper;

    public Compilation patch(Compilation compilation, UpdateCompilationDto patch) {
        if (compilation == null) return null;
        if (patch == null) return compilation;
        Compilation.CompilationBuilder newComp = Compilation.builder();
        newComp.id(compilation.getId());
        if (patch.getEvents() != null) {
            newComp.events(mapper.setToModels(patch.getEvents()));
        } else newComp.events(compilation.getEvents());
        if (patch.getTitle() != null) {
            newComp.title(patch.getTitle());
        } else newComp.title(compilation.getTitle());
        if (patch.getPinned() != null) {
            newComp.pinned(patch.getPinned());
        } else newComp.pinned(compilation.getPinned());
        return newComp.build();
    }
}
