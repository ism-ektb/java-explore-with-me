package ru.practicum.ewm.dto.category.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.model.Category;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CategoryMapper {

    Category dtoToModel(CategoryDto categoryDto);

    CategoryDto modelToDto(Category category);

    @Mapping(source = "categoryId", target = "id")
    Category longToModel(Long categoryId);
}
