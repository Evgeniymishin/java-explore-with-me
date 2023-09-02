package ru.practicum.main.user.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
@Validated
@Hidden
public class UserController {
    private final UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserDto create(@Valid @RequestBody NewUserRequest newUserRequest) {
        return service.create(newUserRequest);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long userId) {
        service.delete(userId);
    }

    @GetMapping
    List<UserDto> getAll(@RequestParam(required = false) List<Long> ids,
                         @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                         @RequestParam(defaultValue = "10") @Positive int size) {
        return service.getAll(ids, from, size);
    }
}

