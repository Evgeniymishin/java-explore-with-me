package ru.practicum.main.category.service;

import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto create(NewCategoryDto newCategoryDto);

    CategoryDto update(Long categoryId, CategoryDto categoryDto);

    public void delete(Long categoryId);

    CategoryDto get(Long categoryId);

    List<CategoryDto> getAll(int from, int size);
}
