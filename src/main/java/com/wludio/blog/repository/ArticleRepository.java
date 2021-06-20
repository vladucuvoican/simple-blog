package com.wludio.blog.repository;

import com.wludio.blog.entites.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static java.lang.String.format;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findByIdAndDeleteIsFalse(Long id);

    default Article findByIdRequired(Long id) {
        return findByIdAndDeleteIsFalse(id).orElseThrow(() -> new IllegalArgumentException(
                format("No Article with the id: %d was found in the database!", id))
        );
    }

    @Query("SELECT a FROM Article a WHERE a.title = :title AND a.delete = false")
    Page<Article> findByTitleAndDeleteIsFalse(@Param("title") String title, Pageable pageable);
}
