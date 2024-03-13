package ru.practicum.ewm.service.category;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.dto.category.CategoryDto;

import java.util.List;

public interface CategoryService {
    /**
     * Создание новой категории
     */
    CategoryDto saveCategory(CategoryDto categoryDto);

    /**
     * Измерение названия категории
     */
    CategoryDto patchCategory(long categoryId, CategoryDto categoryDto);

    /**
     * Удаление категории
     */
    void deleteCategory(long categoryId);

    /**
     * Получение списка всех категорий событий
     */
    List<CategoryDto> findAllCat(Pageable pageable);

    /**
     * Получение категории событий по Id
     */
    CategoryDto findCatById(long id);
}
