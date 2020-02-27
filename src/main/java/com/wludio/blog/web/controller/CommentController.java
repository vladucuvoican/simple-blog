package com.wludio.blog.web.controller;

import com.wludio.blog.facade.FacadeArticleService;
import com.wludio.blog.facade.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/articles/{article_id}/comments")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentController {

    private final FacadeArticleService facadeArticleService;

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable(name = "article_id") @Min(1) Long articleId,
                                                        @RequestParam(defaultValue = "0") final Integer pageNo,
                                                        @RequestParam(defaultValue = "10") final Integer pageSize,
                                                        @RequestParam(defaultValue = "id") final String sortBy) {

        log.info("Request to getComments");
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        List<CommentDto> comments = facadeArticleService.findCommentsForArticle(articleId, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments);
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentDto> create(@PathVariable(name = "article_id") @Min(1) Long articleId,
                                             @Valid @RequestBody final CommentDto comment) {
        log.info("Request to create comment: {}", comment);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(facadeArticleService.addCommentToArticle(comment, articleId));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentDto> update(@PathVariable(name = "article_id") @Min(1) Long articleId,
                                             @PathVariable final Long id,
                                             @Valid @RequestBody final CommentDto comment) {
        log.info("Request to update comment with the id: {} and info: {} ", id, comment);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(facadeArticleService.updateCommentForArticle(id, comment, articleId));
    }
}
