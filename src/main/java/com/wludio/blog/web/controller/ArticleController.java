package com.wludio.blog.web.controller;

import com.wludio.blog.service.ArticleService;
import com.wludio.blog.dtos.ArticleDto;
import com.wludio.blog.dtos.SearchRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Optional;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ArticleDto>> getArticles(Pageable pageable) {
        log.info("Request to getArticles");

        Page<ArticleDto> articles = articleService.findAll(pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(articles);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable @Min(1) final Long id) {
        log.info("Request to getArticleById : {}", id);

        Optional<ArticleDto> article = articleService.findById(id);

        if (article.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(article.get());
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArticleDto> create(@Valid @RequestBody final ArticleDto article) {
        log.info("Request to create for article: {}", article);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(articleService.create(article));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        log.info("Request to delete article with the id : {}", id);

        articleService.delete(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArticleDto> update(@PathVariable @Min(1) final Long id,
                                             @Valid @RequestBody final ArticleDto article) {
        log.info("Request to update article with id : {} and info : {}", id, article);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(articleService.update(id, article));
    }

    @PostMapping(path = "/searches", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ArticleDto>> searchForArticles(@Valid SearchRequest searchRequest,
                                                              Pageable pageable) {
        log.info("Request to search for articles using: {}", searchRequest);

        Page<ArticleDto> articles = articleService.findBySearchRequest(searchRequest, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(articles);
    }
}
