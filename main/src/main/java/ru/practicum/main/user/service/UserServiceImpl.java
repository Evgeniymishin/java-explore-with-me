package ru.practicum.main.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.mapper.UserMapper;
import ru.practicum.main.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserDto create(NewUserRequest newUserRequest) {
        if (userRepository.existsByName(newUserRequest.getName()))
            throw new ConflictException("Невозможно создать пользователя с уже существующим именем.");
        return UserMapper.toUserDto(userRepository.save(UserMapper.toUser(newUserRequest)));
    }

    public void delete(Long userId) {
        userRepository.delete(userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Не найден пользователь с id " + userId)));
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAll(List<Long> userIds, int from, int size) {
        if (userIds == null) {
            return userRepository.findAll(PageRequest.of(from / size, size)).stream()
                    .map(UserMapper::toUserDto)
                    .collect(Collectors.toList());
        } else {
            return userRepository.findUsersByIdIn(userIds, PageRequest.of(from / size, size)).stream()
                    .map(UserMapper::toUserDto)
                    .collect(Collectors.toList());
        }
    }
}
