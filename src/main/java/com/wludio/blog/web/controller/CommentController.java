package com.wludio.blog.web.controller;

import com.wludio.blog.dtos.CommentDto;
import com.wludio.blog.service.CommentService;
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

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/articles/{articleId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<CommentDto>> getComments(@PathVariable(name = "articleId") @Min(1) Long articleId,
                                                        Pageable pageable) {

        log.info("Request to getComments for article with the articleId: {} ", articleId);

        Page<CommentDto> comments = commentService.findCommentsForArticle(articleId, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments);
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentDto> create(@PathVariable(name = "articleId") @Min(1) Long articleId,
                                             @Valid @RequestBody final CommentDto comment) {
        log.info("Request to create comment: {} for article with the articleId: {} ", comment, articleId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.addCommentToArticle(comment, articleId));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentDto> update(@PathVariable(name = "articleId") @Min(1) Long articleId,
                                             @PathVariable final Long id,
                                             @Valid @RequestBody final CommentDto comment) {
        log.info("Request to update comment with the id: {} and info: {} for the article with the articleId: {} ", id, comment, articleId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.updateCommentForArticle(id, comment, articleId));
    }
}
