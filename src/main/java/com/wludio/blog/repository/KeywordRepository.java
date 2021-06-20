package com.wludio.blog.repository;

import com.wludio.blog.entites.Keyword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    Optional<Keyword> findByName(String name);

    @Query("Select k From Keyword k Where k.article.id = :articleId")
    Page<Keyword> findByArticleId(@Param("articleId") Long articleId, Pageable pageable);

}
