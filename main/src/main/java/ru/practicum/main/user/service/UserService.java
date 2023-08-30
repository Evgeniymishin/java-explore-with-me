package ru.practicum.main.user.service;

import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto create(NewUserRequest newUserRequest);

    void delete(Long userId);

    List<UserDto> getAll(List<Long> userIds, int from, int size);
}
