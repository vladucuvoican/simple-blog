package com.wludio.blog.service;


import com.google.common.base.Preconditions;
import com.wludio.blog.entites.Category;
import com.wludio.blog.dtos.CategoryDto;
import com.wludio.blog.mappers.CategoryMapper;
import com.wludio.blog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryDto create(CategoryDto categoryDto) {
        log.debug("Calling create for the category: {}", categoryDto);
        Preconditions.checkNotNull(categoryDto, "The category should not be null!");

        Category category = categoryMapper.toEntity(categoryDto);
        category = categoryRepository.save(category);

        return categoryMapper.toDto(category);
    }

    public Page<CategoryDto> findAll(Pageable pageable) {
        log.debug("Calling findAll");
        Page<Category> categories = categoryRepository.findAll(pageable);

        List<CategoryDto> categoryDtos = categoryMapper.toDtos(categories.getContent());
        return new PageImpl<>(categoryDtos, pageable, categories.getTotalElements());
    }

    public CategoryDto update(Long id, CategoryDto categoryDto) {
        log.debug("Calling update for the category with the id: {} and info: {}", id, categoryDto);
        Preconditions.checkNotNull(categoryDto, "The category should not be null!");
        Preconditions.checkNotNull(id, "The categoryId should not be null!");

        Category category = categoryMapper.toEntity(categoryDto);
        category.setId(id);
        category = categoryRepository.save(category);

        return categoryMapper.toDto(category);
    }

    public Optional<CategoryDto> findById(Long id) {
        log.debug("Calling findById: {}", id);
        Preconditions.checkNotNull(id, "The id should not be null!");

        Category category = categoryRepository.findById(id).get();
        return Optional.ofNullable(categoryMapper.toDto(category));
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
