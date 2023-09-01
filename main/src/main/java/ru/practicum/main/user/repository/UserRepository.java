package ru.practicum.main.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.user.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findUsersByIdIn(List<Long> userIds, Pageable pageable);

    Boolean existsByName(String name);
}
