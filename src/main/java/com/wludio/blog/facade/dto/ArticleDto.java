package com.wludio.blog.facade.dto;

import com.wludio.blog.entites.enums.ArticleStatus;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ArticleDto {
    private Long id;
    private ArticleStatus articleStatus;

    private List<UserDto> authors;

    @NotNull(message = "Please provide a title")
    @Size(min = 2, max = 30, message = "The title should have between 2 and 30 characters")
    private String title;

    @NotNull
    private String content;

    @NotEmpty
    private List<CategoryDto> categories = new ArrayList<>();

    private List<CommentDto> comments = new ArrayList<>();
    private List<KeywordDto> keywords = new ArrayList<>();

    private Date publishedOn;
    private Date createdOn;
    private String createdBy;
    private String modifiedBy;
    private Date modifiedOn;
}
