package ru.practicum.main.category.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
@Validated
@Hidden
public class PublicCategoryController {

    private final CategoryService service;

    @GetMapping("/{categoryId}")
    CategoryDto get(@PathVariable Long categoryId) {
        return service.get(categoryId);
    }

    @GetMapping
    List<CategoryDto> getAll(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
                             @RequestParam(defaultValue = "10") @Positive int size) {
        return service.getAll(from, size);
    }
}
