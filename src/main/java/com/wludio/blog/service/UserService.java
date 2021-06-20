package com.wludio.blog.service;


import com.google.common.base.Preconditions;
import com.wludio.blog.entites.User;
import com.wludio.blog.dtos.UserDto;
import com.wludio.blog.mappers.UserMapper;
import com.wludio.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto create(UserDto userDto) {
        log.debug("Calling create for the user: {}", userDto);
        Preconditions.checkNotNull(userDto, "The user should not be null!");

        User user = userMapper.toEntity(userDto);
        user = userRepository.save(user);

        return userMapper.toDto(user);
    }

    public Page<UserDto> findAll(Pageable pageable) {
        log.debug("Calling findAll");
        Page<User> users = userRepository.findAll(pageable);

        List<UserDto> userDtos = userMapper.toDtos(users.getContent());
        return new PageImpl<>(userDtos, pageable, users.getTotalElements());
    }

    public UserDto update(Long id, UserDto userDto) {
        log.debug("Calling update for the user with the id: {} and info: {}", id, userDto);
        Preconditions.checkNotNull(userDto, "The user should not be null!");
        Preconditions.checkNotNull(id, "The userId should not be null!");

        User user = userMapper.toEntity(userDto);
        user.setId(id);
        user = userRepository.save(user);

        return userMapper.toDto(user);
    }

    public Optional<UserDto> findById(Long id) {
        log.debug("Calling findById: {}", id);
        Preconditions.checkNotNull(id, "The id should not be null!");

        User user = userRepository.findByIdRequired(id);
        return Optional.of(userMapper.toDto(user));
    }

    public void delete(Long id) {
        log.debug("Calling delete for the id: {}", id);
        Preconditions.checkNotNull(id, "The id should not be null!");

        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("There's no user with the id: {}, that can be deleted!", id);
        }
    }
}
