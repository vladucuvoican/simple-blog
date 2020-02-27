package com.wludio.blog.web.controller;

import com.wludio.blog.facade.FacadeArticleService;
import com.wludio.blog.facade.dto.KeywordDto;
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
@RequestMapping("/api/v1/articles/{article_id}/keywords")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KeywordController {

    private final FacadeArticleService facadeArticleService;

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<KeywordDto>> getKeywords(@PathVariable(name = "article_id") @Min(1) Long articleId,
                                                        @RequestParam(defaultValue = "0") final Integer pageNo,
                                                        @RequestParam(defaultValue = "10") final Integer pageSize,
                                                        @RequestParam(defaultValue = "id") final String sortBy) {

        log.info("Request to getKeywords");
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        List<KeywordDto> keywords = facadeArticleService.findKeywordsForArticle(articleId, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(keywords);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KeywordDto> create(@PathVariable(name = "article_id") @Min(1) Long articleId,
                                             @Valid @RequestBody final KeywordDto keyword) {
        log.info("Request to create keyword: {} for article: {}", keyword, articleId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(facadeArticleService.addKeywordToArticle(keyword, articleId));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable(name = "article_id") @Min(1) Long articleId,
                                       @PathVariable @Min(1) final Long id) {
        log.info("Request to delete keyword with the id:{} for article: {}", id, articleId);

        facadeArticleService.deleteKeywordFromArticle(id, articleId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KeywordDto> update(@PathVariable(name = "article_id") @Min(1) Long articleId,
                                             @PathVariable @Min(1) final Long id,
                                             @Valid @RequestBody final KeywordDto keyword) {
        log.info("Request to update keyword with the id: {}, articleId: {} and info: {} ", id, articleId, keyword);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(facadeArticleService.updateKeywordForArticle(id, keyword, articleId));
    }
}
