package com.wludio.blog.web.controller;

import com.wludio.blog.entites.Article;
import com.wludio.blog.facade.FacadeArticleService;
import com.wludio.blog.facade.dto.SearchRequest;
import com.wludio.blog.service.ArticleService;
import com.wludio.blog.util.ResponseEntityFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleController {

    private final ArticleService articleService;
    private final FacadeArticleService facadeArticleService;
    private final ResponseEntityFactory responseEntityFactory;

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Article>> getArticles(@RequestParam(defaultValue = "0") final Integer pageNo,
                                                     @RequestParam(defaultValue = "10") final Integer pageSize,
                                                     @RequestParam(defaultValue = "id") final String sortBy) {
        log.info("Request to getArticles");

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        List<Article> articles = articleService.findAll(pageable);

        return responseEntityFactory.create(articles);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> getArticleById(@PathVariable final Long id) {
        log.info("Request to getArticleById : {}", id);

        Optional<Article> article = articleService.findById(id);

        return responseEntityFactory.create(article);
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> create(@RequestBody final Article article) {
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
    public ResponseEntity<Article> update(@PathVariable final Long id,
                                          @RequestBody final Article article) {
        log.info("Request to update article with id : {} and info : {}", id, article);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(articleService.update(id, article));
    }

    @PostMapping(path = "/searches", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Article>> searchForArticles(SearchRequest searchRequest,
                                                           @RequestParam(defaultValue = "0") final Integer pageNo,
                                                           @RequestParam(defaultValue = "10") final Integer pageSize,
                                                           @RequestParam(defaultValue = "id") final String sortBy) {

        log.info("Request to search for articles");
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        List<Article> articles = facadeArticleService.findBySearchRequest(searchRequest, pageable);

        return responseEntityFactory.create(articles);
    }
}
