package com.wludio.blog.repository;

import com.wludio.blog.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import static java.lang.String.format;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    default User findByIdRequired(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException(
                format("No user  with the id: %d was found in the database!", id))
        );
    }
}
