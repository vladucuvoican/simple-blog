package com.wludio.blog.service;

import com.google.common.base.Preconditions;
import com.wludio.blog.entites.Category;
import com.wludio.blog.repository.CategoryRepository;
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
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category create(Category category) {
        log.debug("Calling create for the category: {}" , category);
        Preconditions.checkNotNull(category, "The category should not be null!");

        return categoryRepository.save(category);
    }

    public List<Category> findAll(Pageable pageable) {
        log.debug("Calling findAll");
        return categoryRepository.findAll(pageable).getContent();
    }

    public Category update(Long id, Category category) {
        log.debug("Calling update for the category with the id: {} and info: {}", id, category);
        Preconditions.checkNotNull(category, "The category should not be null!");
        Preconditions.checkNotNull(id, "The categoryId should not be null!");
        category.setId(id);
        return categoryRepository.save(category);
    }

    public Optional<Category> findById(Long id) {
        log.debug("Calling findById: {}", id);
        Preconditions.checkNotNull(id, "The id should not be null!");

        return categoryRepository.findById(id);
    }

    public Optional<Category> findByName(String name) {
        log.debug("Calling findByName: {}", name);
        Preconditions.checkArgument(StringUtils.isNotBlank(name), "The name should not be blank!");

        return categoryRepository.findByName(name);
    }

    public void delete(Long id) {
        log.debug("Calling delete for the id: {}", id);
        Preconditions.checkNotNull(id, "The id should not be null!");

        try {
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("There's no category with the id: {}, that can be deleted!", id);
        }
    }
}