package com.wludio.blog.repository;

import com.wludio.blog.entites.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findByIdAndDeleteIsFalse(Long id);

    @Query("Select a From Article a Where a.title = :title And a.delete=false")
    List<Article> findByTitleAndDeleteIsFalse(@Param("title") String title, Pageable pageable);
}
