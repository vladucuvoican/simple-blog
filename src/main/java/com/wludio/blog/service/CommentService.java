package com.wludio.blog.service;

import com.google.common.base.Preconditions;
import com.wludio.blog.entites.Comment;
import com.wludio.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment create(Comment comment) {
        log.debug("Calling create for the comment: {}", comment);
        Preconditions.checkNotNull(comment, "The comment should not be null!");

        return commentRepository.save(comment);
    }

    public List<Comment> findAll(Pageable pageable) {
        log.debug("Calling findAll");
        return commentRepository.findAll(pageable).getContent();
    }

    public Comment update(Long id, Comment comment) {
        log.debug("Calling update for the comment with the id: {} and info: {}", id, comment);
        Preconditions.checkNotNull(comment, "The comment should not be null!");
        Preconditions.checkNotNull(id, "The commentId should not be null!");
        comment.setId(id);
        return commentRepository.save(comment);
    }

    public Optional<Comment> findById(Long id) {
        log.debug("Calling findById: {}", id);
        Preconditions.checkNotNull(id, "The id should not be null!");

        return commentRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Calling delete for the id: {}", id);
        Preconditions.checkNotNull(id, "The id should not be null!");

        try {
            commentRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("There's no comment with the id: {}, that can be deleted!", id);
        }
    }

    public List<Comment> findByArticleId(Long articleId, Pageable pageable) {
        log.debug("Calling findByArticleId for the articleId: {}", articleId);
        Preconditions.checkNotNull(articleId, "The articleId should not be null!");

        return commentRepository.findByArticleId(articleId, pageable);
    }
}