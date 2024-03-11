package ru.practicum.ewm.dto.category.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.model.Category;

import java.util.List;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface CategoryListMapper {
    List<CategoryDto> modelsToDtos(List<Category> categories);
}
