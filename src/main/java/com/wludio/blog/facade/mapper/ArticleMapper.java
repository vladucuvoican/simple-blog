package com.wludio.blog.facade.mapper;

import com.wludio.blog.entites.Article;
import com.wludio.blog.facade.dto.ArticleDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class ArticleMapper {

    public abstract ArticleDto toDto(Article article);
    public abstract List<ArticleDto> toDtos(List<Article> articleList);

    public abstract Article toEntity(ArticleDto articleDto);
    public abstract List<Article> toEntities(List<ArticleDto> articleDtoList);
}
