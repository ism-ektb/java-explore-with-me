package ru.practicum.ewm.controller.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.service.category.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@Validated
public class PublicCatController {

    private final CategoryService service;

    @GetMapping
    public List<CategoryDto> findAllCat(@RequestParam(required = false, defaultValue = "0") @PositiveOrZero int from,
                                        @RequestParam(required = false, defaultValue = "10") @Positive int size) {
        return service.findAllCat(PageRequest.of(from / size, size));
    }

    @GetMapping("/{catId}")
    public CategoryDto findCatById(@PathVariable long catId) {
        return service.findCatById(catId);
    }

}
