package ru.practicum.ewm.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.dto.category.mapper.CategoryListMapper;
import ru.practicum.ewm.dto.category.mapper.CategoryMapper;
import ru.practicum.ewm.exception.exception.NoFoundObjectException;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.repositiry.CategoryRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    private final CategoryListMapper listMapper;

    /**
     * Создание новой категории
     */
    @Override
    public CategoryDto saveCategory(CategoryDto categoryDto) {
        return mapper.modelToDto(repository.save(mapper.dtoToModel(categoryDto)));
    }

    /**
     * Измерение названия категории
     */
    @Override
    @Transactional
    public CategoryDto patchCategory(long catId, CategoryDto patch) {
        Category category = repository.findById(catId).orElseThrow(()
                -> new NoFoundObjectException(String.format("Category with id=%s was not found", catId)));

        return mapper.modelToDto(repository.save(Category.builder()
                .id(category.getId())
                .name(patch.getName()).build()));
    }

    /**
     * Удаление категории
     */
    @Override
    public void deleteCategory(long categoryId) {

        if (repository.deleteByIdAndReturnCount(categoryId) == 0)
            throw new NoFoundObjectException(String.format("Категория с id='%s' не найден", categoryId));
    }

    /**
     * Получение списка всех категорий событий
     */
    @Override
    public List<CategoryDto> findAllCat(Pageable pageable) {
        return listMapper.modelsToDtos(repository.findAll(pageable).getContent());
    }

    /**
     * Получение категории событий по Id
     */
    @Override
    public CategoryDto findCatById(long id) {
        return mapper.modelToDto(repository.findById(id).orElseThrow(()
                -> new NoFoundObjectException(String.format("Category with id=%s was not found", id))));
    }
}
