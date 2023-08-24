package ru.practicum.main.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
public class UserController {
    private final UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserDto create(@Valid @RequestBody UserDto userDto) {
        return service.create(userDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long userId) {
        service.delete(userId);
    }

    @GetMapping
    List<UserDto> getAll(@RequestParam(required = false) List<Long> ids,
                         @RequestParam(defaultValue = "0") int from,
                         @RequestParam(defaultValue = "10") int size) {
        return service.getAll(ids, from, size);
    }
}

