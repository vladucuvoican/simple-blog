package com.wludio.blog.facade;


import com.google.common.base.Preconditions;
import com.wludio.blog.entites.User;
import com.wludio.blog.facade.dto.UserDto;
import com.wludio.blog.facade.mapper.UserMapper;
import com.wludio.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FacadeUserService {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserDto create(UserDto userDto) {
        log.debug("Calling create for the user: {}", userDto);
        Preconditions.checkNotNull(userDto, "The user should not be null!");

        User user = userMapper.toEntity(userDto);
        user = userService.create(user);

        return userMapper.toDto(user);
    }

    public List<UserDto> findAll(Pageable pageable) {
        log.debug("Calling findAll");
        List<User> users = userService.findAll(pageable);

        return userMapper.toDtos(users);
    }

    public UserDto update(Long id, UserDto userDto) {
        log.debug("Calling update for the user with the id: {} and info: {}", id, userDto);
        Preconditions.checkNotNull(userDto, "The user should not be null!");
        Preconditions.checkNotNull(id, "The userId should not be null!");

        User user = userMapper.toEntity(userDto);
        user = userService.update(id, user);

        return userMapper.toDto(user);
    }

    public Optional<UserDto> findById(Long id) {
        log.debug("Calling findById: {}", id);
        Preconditions.checkNotNull(id, "The id should not be null!");

        User user = userService.findById(id).get();
        return Optional.ofNullable(userMapper.toDto(user));
    }

    public void delete(Long id) {
        log.debug("Calling delete for the id: {}", id);
        Preconditions.checkNotNull(id, "The id should not be null!");

        userService.delete(id);
    }
}
