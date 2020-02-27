package com.wludio.blog.service;

import com.google.common.base.Preconditions;
import com.wludio.blog.entites.Keyword;
import com.wludio.blog.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KeywordService {

    private final KeywordRepository keywordRepository;

    public Keyword create(Keyword keyword) {
        log.debug("Calling create for the keyword: {}" , keyword);
        Preconditions.checkNotNull(keyword, "The keyword should not be null!");

        return keywordRepository.save(keyword);
    }

    public List<Keyword> findAll(Pageable pageable) {
        log.debug("Calling findAll");
        return keywordRepository.findAll(pageable).getContent();
    }

    public Keyword update(Long id, Keyword keyword) {
        log.debug("Calling update for the keyword with the id: {} and info: {}", id, keyword);
        Preconditions.checkNotNull(keyword, "The keyword should not be null!");
        Preconditions.checkNotNull(id, "The keywordId should not be null!");
        keyword.setId(id);
        return keywordRepository.save(keyword);
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

    public void delete(Long id) {
        log.debug("Calling delete for the id: {}", id);
        Preconditions.checkNotNull(id, "The id should not be null!");

        try {
            keywordRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("There's no keyword with the id: {}, that can be deleted!", id);
        }
    }

    public List<Keyword> findByArticleId(Long articleId, Pageable pageable) {
        log.debug("Calling findByArticleId for the articleId: {}", articleId);
        Preconditions.checkNotNull(articleId, "The articleId should not be null!");

        return keywordRepository.findByArticleId(articleId, pageable);
    }

}