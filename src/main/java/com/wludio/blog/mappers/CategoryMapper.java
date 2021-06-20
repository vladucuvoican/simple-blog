package com.wludio.blog.mappers;

import com.wludio.blog.entites.Category;
import com.wludio.blog.dtos.CategoryDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class CategoryMapper {

    public abstract CategoryDto toDto(Category category);
    public abstract List<CategoryDto> toDtos(List<Category> categoryList);

    public abstract Category toEntity(CategoryDto categoryDto);
    public abstract List<Category> toEntities(List<CategoryDto> categoryDtoList);
}
