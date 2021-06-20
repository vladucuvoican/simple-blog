package com.wludio.blog.repository;

import com.wludio.blog.entites.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("Select c From Comment c Where c.article.id = :articleId")
    Page<Comment> findByArticleId(@Param("articleId") Long articleId, Pageable pageable);
}
