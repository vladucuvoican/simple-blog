package com.wludio.blog.service;


import com.google.common.base.Preconditions;
import com.wludio.blog.entites.Article;
import com.wludio.blog.dtos.ArticleDto;
import com.wludio.blog.dtos.SearchRequest;
import com.wludio.blog.mappers.ArticleMapper;
import com.wludio.blog.repository.ArticleRepository;
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
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    // TODO change this method to query by keywords a well in a dynamic way
    public Page<ArticleDto> findBySearchRequest(SearchRequest searchRequest, Pageable pageable) {
        log.debug("Calling findBySearchRequest for the searchRequest: {}", searchRequest);

        Page<Article> articles = articleRepository.findByTitleAndDeleteIsFalse(searchRequest.getTitle(), pageable);

        List<ArticleDto> articleDtos = articleMapper.toDtos(articles.getContent());

        return new PageImpl<>(articleDtos, pageable, articles.getTotalElements());
    }

    public ArticleDto create(ArticleDto articleDto) {
        log.debug("Calling create for the article: {}", articleDto);
        Preconditions.checkNotNull(articleDto, "The article should not be null!");

        Article article = articleMapper.toEntity(articleDto);
        article = articleRepository.save(article);

        return articleMapper.toDto(article);
    }

    public Page<ArticleDto> findAll(Pageable pageable) {
        log.debug("Calling findAll");

        Page<Article> articles = articleRepository.findAll(pageable);

        List<ArticleDto> articleDtos = articleMapper.toDtos(articles.getContent());

        return new PageImpl<>(articleDtos, pageable, articles.getTotalElements());
    }

    public ArticleDto update(Long id, ArticleDto articleDto) {
        log.debug("Calling update for the article with the id: {} and info: {}", id, articleDto);
        Preconditions.checkNotNull(articleDto, "The article should not be null!");
        Preconditions.checkNotNull(id, "The articleId should not be null!");

        Article article = articleMapper.toEntity(articleDto);
        article.setId(id);
        article = articleRepository.save(article);

        return articleMapper.toDto(article);
    }

    public Optional<ArticleDto> findById(Long id) {
        log.debug("Calling findById: {}", id);
        Preconditions.checkNotNull(id, "The id should not be null!");

        Optional<Article> optionalArticle = articleRepository.findByIdAndDeleteIsFalse(id);
        if (optionalArticle.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(articleMapper.toDto(optionalArticle.get()));
    }

    public Page<ArticleDto> findByTitle(String title, Pageable pageable) {
        log.debug("Calling findByTitle: {}", title);
        Preconditions.checkArgument(StringUtils.isNotBlank(title), "The title should not be blank!");

        Page<Article> articles = articleRepository.findByTitleAndDeleteIsFalse(title, pageable);

        List<ArticleDto> articleDtos = articleMapper.toDtos(articles.getContent());

        return new PageImpl<>(articleDtos, pageable, articles.getTotalElements());
    }

    public void delete(Long id) {
        log.debug("Calling delete for the id: {}", id);
        Preconditions.checkNotNull(id, "The id should not be null!");

        try {
            articleRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("There's no article with the id: {}, that can be deleted!", id);
        }
    }
}
