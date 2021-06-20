package com.wludio.blog.service;

import com.google.common.base.Preconditions;
import com.wludio.blog.entites.Article;
import com.wludio.blog.entites.Comment;
import com.wludio.blog.dtos.CommentDto;
import com.wludio.blog.mappers.CommentMapper;
import com.wludio.blog.repository.ArticleRepository;
import com.wludio.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentDto addCommentToArticle(CommentDto commentDto, Long articleId) {
        log.debug("Calling addCommentToArticle: comment {}, articleId {}", commentDto, articleId);
        Preconditions.checkNotNull(articleId, "The articleId should not be null!");
        Preconditions.checkNotNull(commentDto, "The comment should not be null!");

        Article article = articleRepository.findById(articleId).get();
        Comment comment = commentMapper.toEntity(commentDto);
        comment.setArticle(article);
        comment = commentRepository.save(comment);

        return commentMapper.toDto(comment);
    }

    public CommentDto updateCommentForArticle(Long id, CommentDto commentDto, Long articleId) {
        log.debug("Calling addCommentToArticle: comment {}, articleId {}", commentDto, articleId);
        Preconditions.checkNotNull(id, "The id should not be null!");
        Preconditions.checkNotNull(articleId, "The articleId should not be null!");
        Preconditions.checkNotNull(commentDto, "The comment should not be null!");

        Article article = articleRepository.findByIdRequired(articleId);
        Comment comment = commentMapper.toEntity(commentDto);
        comment.setArticle(article);
        comment.setId(id);
        comment = commentRepository.save(comment);

        return commentMapper.toDto(comment);
    }

    public Page<CommentDto> findCommentsForArticle(Long articleId, Pageable pageable) {
        log.debug("Calling findCommentsForArticle using the articleId {}", articleId);
        Preconditions.checkNotNull(articleId, "The articleId should not be null!");

        Page<Comment> comments = commentRepository.findByArticleId(articleId, pageable);

        List<CommentDto> commentDtos = commentMapper.toDtos(comments.getContent());
        return new PageImpl<>(commentDtos, pageable, comments.getTotalElements());
    }

    public Comment create(Comment comment) {
        log.debug("Calling create for the comment: {}", comment);
        Preconditions.checkNotNull(comment, "The comment should not be null!");

        return commentRepository.save(comment);
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
}
