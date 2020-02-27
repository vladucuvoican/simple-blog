package com.wludio.blog.facade;


import com.google.common.base.Preconditions;
import com.wludio.blog.entites.Category;
import com.wludio.blog.facade.dto.CategoryDto;
import com.wludio.blog.facade.mapper.CategoryMapper;
import com.wludio.blog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FacadeCategoryService {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryDto create(CategoryDto categoryDto) {
        log.debug("Calling create for the category: {}", categoryDto);
        Preconditions.checkNotNull(categoryDto, "The category should not be null!");

        Category category = categoryMapper.toEntity(categoryDto);
        category = categoryService.create(category);

        return categoryMapper.toDto(category);
    }

    public List<CategoryDto> findAll(Pageable pageable) {
        log.debug("Calling findAll");
        List<Category> categorys = categoryService.findAll(pageable);

        return categoryMapper.toDtos(categorys);
    }

    public CategoryDto update(Long id, CategoryDto categoryDto) {
        log.debug("Calling update for the category with the id: {} and info: {}", id, categoryDto);
        Preconditions.checkNotNull(categoryDto, "The category should not be null!");
        Preconditions.checkNotNull(id, "The categoryId should not be null!");

        Category category = categoryMapper.toEntity(categoryDto);
        category = categoryService.update(id, category);

        return categoryMapper.toDto(category);
    }

    public Optional<CategoryDto> findById(Long id) {
        log.debug("Calling findById: {}", id);
        Preconditions.checkNotNull(id, "The id should not be null!");

        Category category = categoryService.findById(id).get();
        return Optional.ofNullable(categoryMapper.toDto(category));
    }

    public void delete(Long id) {
        log.debug("Calling delete for the id: {}", id);
        Preconditions.checkNotNull(id, "The id should not be null!");

        categoryService.delete(id);
    }
}
