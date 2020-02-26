package com.wludio.blog.web.controller;

import com.wludio.blog.entites.User;
import com.wludio.blog.service.UserService;
import com.wludio.blog.util.ResponseEntityFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    private final ResponseEntityFactory responseEntityFactory;

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUsers(@RequestParam(defaultValue = "0") final Integer pageNo,
                                               @RequestParam(defaultValue = "10") final Integer pageSize,
                                               @RequestParam(defaultValue = "id") final String sortBy) {

        log.info("Request to getUsers");
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        List<User> users = userService.findAll(pageable);

        return responseEntityFactory.create(users);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable final Long id) {

        log.info("Request to getUserById : {}", id);

        Optional<User> user = userService.findById(id);

        return responseEntityFactory.create(user);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@RequestBody final User user) {
        log.info("Request to create user: {}", user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(user));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        log.info("Request to delete user with the id : {}", id);

        userService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> update(@PathVariable final Long id,
                                       @RequestBody final User user) {
        log.info("Request to update user with the id: {} and info: {} ", id, user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.update(id, user));
    }
}
