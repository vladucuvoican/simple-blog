package com.wludio.blog.service;

import com.google.common.base.Preconditions;
import com.wludio.blog.entites.Article;
import com.wludio.blog.entites.Keyword;
import com.wludio.blog.dtos.KeywordDto;
import com.wludio.blog.mappers.KeywordMapper;
import com.wludio.blog.repository.ArticleRepository;
import com.wludio.blog.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;
    private final ArticleRepository articleRepository;
    private final KeywordMapper keywordMapper;

    public Page<KeywordDto> findKeywordsForArticle(Long articleId, Pageable pageable) {
        log.debug("Calling findKeywordsForArticle using the articleId {}", articleId);
        Preconditions.checkNotNull(articleId, "The articleId should not be null!");

        Page<Keyword> keywords = keywordRepository.findByArticleId(articleId, pageable);

        List<KeywordDto> keywordDtos = keywordMapper.toDtos(keywords.getContent());

        return new PageImpl<>(keywordDtos, pageable, keywords.getTotalElements());
    }

    public KeywordDto addKeywordToArticle(KeywordDto keywordDto, Long articleId) {
        log.debug("Calling addKeywordToArticle: keyword {}, id {}", keywordDto, articleId);
        Preconditions.checkNotNull(articleId, "The id should not be null!");
        Preconditions.checkNotNull(keywordDto, "The keywordDto should not be null!");

        Article article = articleRepository.findByIdRequired(articleId);
        Keyword keyword = keywordMapper.toEntity(keywordDto);
        keyword.setArticle(article);
        keyword = keywordRepository.save(keyword);

        return keywordMapper.toDto(keyword);
    }

    public KeywordDto updateKeywordForArticle(Long id, KeywordDto keywordDto, Long articleId) {
        log.debug("Calling updateKeywordForArticle: keyword {}, id {}", keywordDto, articleId);
        Preconditions.checkNotNull(id, "The id should not be null!");
        Preconditions.checkNotNull(articleId, "The articleId should not be null!");
        Preconditions.checkNotNull(keywordDto, "The keywordDto should not be null!");

        Article article = articleRepository.findByIdRequired(articleId);
        Keyword keyword = keywordMapper.toEntity(keywordDto);
        keyword.setArticle(article);
        keyword.setId(id);
        keyword = keywordRepository.save(keyword);

        return keywordMapper.toDto(keyword);
    }

    public void deleteKeywordFromArticle(Long id, Long articleId) {
        log.debug("Calling deleteKeywordFromArticle: id {}, articleId {}", id, articleId);
        Preconditions.checkNotNull(id, "The id should not be null!");
        Preconditions.checkNotNull(articleId, "The articleId should not be null!");

        try {
            keywordRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("There's no keyword with the id: {}, that can be deleted!", id);
        }
    }

    public Optional<Keyword> findById(Long id) {
        log.debug("Calling findById: {}", id);
        Preconditions.checkNotNull(id, "The id should not be null!");

        return keywordRepository.findById(id);
    }

    public Optional<Keyword> findByName(String name) {
        log.debug("Calling findByName: {}", name);
        Preconditions.checkArgument(StringUtils.isNotBlank(name), "The name should not be blank!");

        return keywordRepository.findByName(name);
    }

    public Page<Keyword> findByArticleId(Long articleId, Pageable pageable) {
        log.debug("Calling findByArticleId for the articleId: {}", articleId);
        Preconditions.checkNotNull(articleId, "The articleId should not be null!");

        return keywordRepository.findByArticleId(articleId, pageable);
    }
}
