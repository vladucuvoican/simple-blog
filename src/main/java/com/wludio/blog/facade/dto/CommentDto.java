package com.wludio.blog.facade.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
public class CommentDto {

    private Long id;

    private CommentDto parent;
    private List<CommentDto> children = new ArrayList<>();

    @NotNull(message = "Please provide a title")
    @Size(min = 3, max = 30, message = "The title should have between 3 and 30 characters")
    private String title;

    @NotNull(message = "Please provide a content")
    @Size(min = 3, max = 500, message = "The content should have between 3 and 500 characters")
    private String content;

    private Date publishedOn;
    private Date createdOn;
    private String createdBy;
    private String modifiedBy;
    private Date modifiedOn;
}