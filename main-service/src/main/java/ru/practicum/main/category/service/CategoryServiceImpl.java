package ru.practicum.main.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;
import ru.practicum.main.category.mapper.CategoryMapper;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.category.repository.CategoryRepository;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    public CategoryDto create(NewCategoryDto newCategoryDto) {
        if (categoryRepository.existsByName(newCategoryDto.getName()))
            throw new ConflictException("Категория " + newCategoryDto.getName() + " уже существует");
        return CategoryMapper.toCategoryDto(categoryRepository.save(CategoryMapper.toCategory(newCategoryDto)));
    }

    public void delete(Long categoryId) {
        if (!eventRepository.findEventsByCategoryId(categoryId).isEmpty())
            throw new ConflictException("Невозможно удалить категорию, в которой есть созданные события.");
        categoryRepository.delete(categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Не найдена категория с id " + categoryId)));
    }

    public CategoryDto update(Long categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Не найдена категория с id " + categoryId));
        if (categoryRepository.existsByName(categoryDto.getName()) && !categoryDto.getName().equals(category.getName())) {
            throw new ConflictException("Категория " + categoryDto.getName() + " уже существует");
        }
        category.setName(categoryDto.getName());
        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Transactional(readOnly = true)
    public CategoryDto get(Long categoryId) {
        return CategoryMapper.toCategoryDto(categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Не найдена категория с id " + categoryId)));
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> getAll(int from, int size) {
        return categoryRepository.findAll(PageRequest.of(from / size, size)).stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }
}
