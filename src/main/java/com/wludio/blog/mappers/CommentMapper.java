package com.wludio.blog.mappers;

import com.wludio.blog.entites.Comment;
import com.wludio.blog.dtos.CommentDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class CommentMapper {

    public abstract CommentDto toDto(Comment comment);
    public abstract List<CommentDto> toDtos(List<Comment> commentList);

    public abstract Comment toEntity(CommentDto commentDto);
    public abstract List<Comment> toEntities(List<CommentDto> commentDtoList);
}
