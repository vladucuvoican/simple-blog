package com.wludio.blog.service;

import com.google.common.base.Preconditions;
import com.wludio.blog.entites.Article;
import com.wludio.blog.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Article create(Article article) {
        log.debug("Calling create for the article: {}" , article);
        Preconditions.checkNotNull(article, "The article should not be null!");

        return articleRepository.save(article);
    }

    public List<Article> findAll(Pageable pageable) {
        log.debug("Calling findAll");
        return articleRepository.findAll(pageable).getContent();
    }

    public Article update(Long id, Article article) {
        log.debug("Calling update for the article with the id: {} and info: {}", id, article);
        Preconditions.checkNotNull(article, "The article should not be null!");
        Preconditions.checkNotNull(id, "The articleId should not be null!");
        article.setId(id);
        return articleRepository.save(article);
    }

    public Optional<Article> findById(Long id) {
        log.debug("Calling findById: {}", id);
        Preconditions.checkNotNull(id, "The id should not be null!");

        return articleRepository.findByIdAndDeleteIsFalse(id);
    }

    public List<Article> findByTitle(String title, Pageable pageable) {
        log.debug("Calling findByTitle: {}", title);
        Preconditions.checkArgument(StringUtils.isNotBlank(title), "The title should not be blank!");

        return articleRepository.findByTitleAndDeleteIsFalse(title, pageable);
    }

    public void delete(Long id) {
        log.debug("Calling delete for the id: {}", id);
        Preconditions.checkNotNull(id, "The id should not be null!");

        articleRepository.deleteById(id);
    }
}