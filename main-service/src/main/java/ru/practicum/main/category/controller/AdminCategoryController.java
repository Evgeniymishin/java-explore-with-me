package ru.practicum.main.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;
import ru.practicum.main.category.service.CategoryService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
public class AdminCategoryController {
    private final CategoryService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CategoryDto create(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        return service.create(newCategoryDto);
    }

    @PatchMapping("/{categoryId}")
    CategoryDto update(@PathVariable Long categoryId,
                       @Valid @RequestBody CategoryDto categoryDto) {
        return service.update(categoryId, categoryDto);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long categoryId) {
        service.delete(categoryId);
    }
}
