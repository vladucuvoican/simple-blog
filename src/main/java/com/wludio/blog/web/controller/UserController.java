package com.wludio.blog.web.controller;

import com.wludio.blog.service.UserService;
import com.wludio.blog.dtos.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Optional;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<UserDto>> getUserDtos(Pageable pageable) {
        log.info("Request to getUsers");

        Page<UserDto> users = userService.findAll(pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(users);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUserDtoById(@PathVariable @Min(1) final Long id) {
        log.info("Request to getUserById : {}", id);

        Optional<UserDto> user = userService.findById(id);

        if (user.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(user.get());
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> create(@Valid @RequestBody final UserDto user) {
        log.info("Request to create user: {}", user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(user));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable @Min(1) final Long id) {
        log.info("Request to delete user with the id : {}", id);

        userService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> update(@PathVariable @Min(1) final Long id,
                                          @Valid @RequestBody final UserDto user) {
        log.info("Request to update user with the id: {} and info: {} ", id, user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.update(id, user));
    }
}
