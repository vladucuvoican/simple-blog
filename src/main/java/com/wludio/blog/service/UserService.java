package com.wludio.blog.service;

import com.google.common.base.Preconditions;
import com.wludio.blog.entites.User;
import com.wludio.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;

    public void updateLastLoginDateForUser(String username) {
        log.debug("Calling updateLastLoginDateForUser with the username: {}", username);
        Preconditions.checkArgument(StringUtils.isNotBlank(username), "The username should not be blank!");

        User user = userRepository.findByUsername(username);
        user.setLastLogin(Date.from(Instant.now()));
        userRepository.save(user);
    }

    public List<User> findAll(Pageable pageable) {
        log.debug("Calling findAll");

        return userRepository.findAll(pageable).getContent();
    }

    public Optional<User> findById(Long id) {
        log.debug("Calling findById: {}", id);
        Preconditions.checkNotNull(id, "The userId should not be null!");

        return userRepository.findById(id);
    }

    public User create(User user) {
        log.debug("Calling create for the user: {}" , user);
        Preconditions.checkNotNull(user, "The user should not be null!");

        return userRepository.save(user);
    }

    public User update(Long id, User user) {
        log.debug("Calling update for the user with the id: {} and info: {}", id, user);
        Preconditions.checkNotNull(user, "The user should not be null!");
        Preconditions.checkNotNull(id, "The userId should not be null!");

        user.setId(id);
        return userRepository.save(user);
    }

    public void delete(Long id) {
        log.debug("Calling delete user with the id: {}", id);
        Preconditions.checkNotNull(id, "The userId should not be null!");

        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("There's no user with the id: {}, that can be deleted!", id);
        }
    }
}
