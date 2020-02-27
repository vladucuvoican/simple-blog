package com.wludio.blog.facade;


import com.google.common.base.Preconditions;
import com.wludio.blog.entites.Article;
import com.wludio.blog.entites.Comment;
import com.wludio.blog.entites.Keyword;
import com.wludio.blog.facade.dto.ArticleDto;
import com.wludio.blog.facade.dto.CommentDto;
import com.wludio.blog.facade.dto.KeywordDto;
import com.wludio.blog.facade.dto.SearchRequest;
import com.wludio.blog.facade.mapper.ArticleMapper;
import com.wludio.blog.facade.mapper.CommentMapper;
import com.wludio.blog.facade.mapper.KeywordMapper;
import com.wludio.blog.service.ArticleService;
import com.wludio.blog.service.CommentService;
import com.wludio.blog.service.KeywordService;
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
public class FacadeArticleService {

    private final ArticleService articleService;
    private final CommentService commentService;
    private final KeywordService keywordService;

    private final ArticleMapper articleMapper;
    private final CommentMapper commentMapper;
    private final KeywordMapper keywordMapper;

    public List<ArticleDto> findBySearchRequest(SearchRequest searchRequest, Pageable pageable) {
        log.debug("Calling findBySearchRequest for the searchRequest: {}", searchRequest);
        // TODO change this method to query by keywords a well in a dynamic way
        List<Article> articles = articleService.findByTitle(searchRequest.getTitle(), pageable);

        return articleMapper.toDtos(articles);
    }

    public ArticleDto create(ArticleDto articleDto) {
        log.debug("Calling create for the article: {}", articleDto);
        Preconditions.checkNotNull(articleDto, "The article should not be null!");

        Article article = articleMapper.toEntity(articleDto);
        article = articleService.create(article);

        return articleMapper.toDto(article);
    }

    public List<ArticleDto> findAll(Pageable pageable) {
        log.debug("Calling findAll");
        List<Article> articles = articleService.findAll(pageable);

        return articleMapper.toDtos(articles);
    }

    public ArticleDto update(Long id, ArticleDto articleDto) {
        log.debug("Calling update for the article with the id: {} and info: {}", id, articleDto);
        Preconditions.checkNotNull(articleDto, "The article should not be null!");
        Preconditions.checkNotNull(id, "The articleId should not be null!");

        Article article = articleMapper.toEntity(articleDto);
        article = articleService.update(id, article);

        return articleMapper.toDto(article);
    }

    public Optional<ArticleDto> findById(Long id) {
        log.debug("Calling findById: {}", id);
        Preconditions.checkNotNull(id, "The id should not be null!");

        Article article = articleService.findById(id).get();
        return Optional.ofNullable(articleMapper.toDto(article));
    }

    public List<ArticleDto> findByTitle(String title, Pageable pageable) {
        log.debug("Calling findByTitle: {}", title);
        Preconditions.checkArgument(StringUtils.isNotBlank(title), "The title should not be blank!");

        List<Article> articles = articleService.findByTitle(title, pageable);
        return articleMapper.toDtos(articles);
    }

    public void delete(Long id) {
        log.debug("Calling delete for the id: {}", id);
        Preconditions.checkNotNull(id, "The id should not be null!");

        articleService.delete(id);
    }

    public CommentDto addCommentToArticle(CommentDto commentDto, Long articleId) {
        log.debug("Calling addCommentToArticle: comment {}, articleId {}", commentDto, articleId);
        Preconditions.checkNotNull(articleId, "The articleId should not be null!");
        Preconditions.checkNotNull(commentDto, "The comment should not be null!");

        Article article = articleService.findById(articleId).get();
        Comment comment = commentMapper.toEntity(commentDto);
        comment.setArticle(article);
        comment = commentService.create(comment);

        return commentMapper.toDto(comment);
    }

    public CommentDto updateCommentForArticle(Long id, CommentDto commentDto, Long articleId) {
        log.debug("Calling addCommentToArticle: comment {}, articleId {}", commentDto, articleId);
        Preconditions.checkNotNull(id, "The id should not be null!");
        Preconditions.checkNotNull(articleId, "The articleId should not be null!");
        Preconditions.checkNotNull(commentDto, "The comment should not be null!");

        Article article = articleService.findById(articleId).get();
        Comment comment = commentMapper.toEntity(commentDto);
        comment.setArticle(article);
        comment = commentService.update(id, comment);

        return commentMapper.toDto(comment);
    }

    public List<CommentDto> findCommentsForArticle(Long articleId, Pageable pageable) {
        log.debug("Calling findCommentsForArticle using the articleId {}", articleId);
        Preconditions.checkNotNull(articleId, "The articleId should not be null!");

        List<Comment> comments = commentService.findByArticleId(articleId, pageable);

        return commentMapper.toDtos(comments);
    }

    public List<KeywordDto> findKeywordsForArticle(Long articleId, Pageable pageable) {
        log.debug("Calling findKeywordsForArticle using the articleId {}", articleId);
        Preconditions.checkNotNull(articleId, "The articleId should not be null!");

        List<Keyword> keywords = keywordService.findByArticleId(articleId, pageable);

        return keywordMapper.toDtos(keywords);
    }

    public KeywordDto addKeywordToArticle(KeywordDto keywordDto, Long articleId) {
        log.debug("Calling addKeywordToArticle: keyword {}, id {}", keywordDto, articleId);
        Preconditions.checkNotNull(articleId, "The id should not be null!");
        Preconditions.checkNotNull(keywordDto, "The keywordDto should not be null!");

        Article article = articleService.findById(articleId).get();
        Keyword keyword = keywordMapper.toEntity(keywordDto);
        keyword.setArticle(article);
        keyword = keywordService.create(keyword);

        return keywordMapper.toDto(keyword);
    }

    public KeywordDto updateKeywordForArticle(Long id, KeywordDto keywordDto, Long articleId) {
        log.debug("Calling updateKeywordForArticle: keyword {}, id {}", keywordDto, articleId);
        Preconditions.checkNotNull(id, "The id should not be null!");
        Preconditions.checkNotNull(articleId, "The articleId should not be null!");
        Preconditions.checkNotNull(keywordDto, "The keywordDto should not be null!");

        Article article = articleService.findById(articleId).get();
        Keyword keyword = keywordMapper.toEntity(keywordDto);
        keyword.setArticle(article);
        keyword = keywordService.update(id, keyword);

        return keywordMapper.toDto(keyword);
    }

    public void deleteKeywordFromArticle(Long id, Long articleId) {
        log.debug("Calling deleteKeywordFromArticle: id {}, articleId {}", id, articleId);
        Preconditions.checkNotNull(id, "The id should not be null!");
        Preconditions.checkNotNull(articleId, "The articleId should not be null!");

        keywordService.delete(id);
    }
}
