package com.wludio.blog.facade.mapper;

import com.wludio.blog.entites.Keyword;
import com.wludio.blog.facade.dto.KeywordDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class KeywordMapper {

    public abstract KeywordDto toDto(Keyword keyword);
    public abstract List<KeywordDto> toDtos(List<Keyword> keywordList);

    public abstract Keyword toEntity(KeywordDto keywordDto);
    public abstract List<Keyword> toEntities(List<KeywordDto> keywordDtoList);
}
