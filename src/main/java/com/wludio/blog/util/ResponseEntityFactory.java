package com.wludio.blog.util;

import com.wludio.blog.entites.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Optional;

/**
 * Factory used to create ResponseEntity
 */
@Component
public final class ResponseEntityFactory {

    /**
     * Creates a {@link ResponseEntity} with Status 200 if the optional has a value or otherwise it creates one with Status 204.
     */
    public <T>  ResponseEntity<T> create(Optional<T> optional) {
        if (optional.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(optional.get());
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    /**
     * Creates a {@link ResponseEntity} with Status 200 if the Object is valid or otherwise it creates one with Status 204.
     */
    public <T> ResponseEntity<T> create(T object) {
        if (object != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(object);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    /**
     * Creates a {@link ResponseEntity} with Status 200 if the collection is valid or otherwise it creates one with Status 204.
     */
    public <T> ResponseEntity create(Collection<T> collection) {
        if (!CollectionUtils.isEmpty(collection)) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(collection);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
