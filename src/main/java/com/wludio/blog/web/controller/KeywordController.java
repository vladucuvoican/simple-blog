package com.wludio.blog.web.controller;

import com.wludio.blog.dtos.KeywordDto;
import com.wludio.blog.service.KeywordService;
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
@RequestMapping("/api/v1/articles/{articleId}/keywords")
@RequiredArgsConstructor
public class KeywordController {

    private final KeywordService keywordService;

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<KeywordDto>> getKeywords(@PathVariable(name = "articleId") @Min(1) Long articleId,
                                                        Pageable pageable) {
        log.info("Request to getKeywords");

        Page<KeywordDto> keywords = keywordService.findKeywordsForArticle(articleId, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(keywords);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KeywordDto> create(@PathVariable(name = "articleId") @Min(1) Long articleId,
                                             @Valid @RequestBody final KeywordDto keyword) {
        log.info("Request to create keyword: {} for article: {}", keyword, articleId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(keywordService.addKeywordToArticle(keyword, articleId));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable(name = "articleId") @Min(1) Long articleId,
                                       @PathVariable @Min(1) final Long id) {
        log.info("Request to delete keyword with the id:{} for article: {}", id, articleId);

        keywordService.deleteKeywordFromArticle(id, articleId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KeywordDto> update(@PathVariable(name = "articleId") @Min(1) Long articleId,
                                             @PathVariable @Min(1) final Long id,
                                             @Valid @RequestBody final KeywordDto keyword) {
        log.info("Request to update keyword with the id: {}, articleId: {} and info: {} ", id, articleId, keyword);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(keywordService.updateKeywordForArticle(id, keyword, articleId));
    }
}
