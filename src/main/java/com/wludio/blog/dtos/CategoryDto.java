package com.wludio.blog.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryDto {

    private Long id;
    private CategoryDto parent;
    private List<CategoryDto> children = new ArrayList<>();

    @NotNull(message = "Please provide a name")
    @Size(min = 3, max = 30, message = "The name should have between 3 and 30 characters")
    private String name;

    @NotNull(message = "Please provide a description")
    @Size(min = 3, max = 500, message = "The description should have between 3 and 500 characters")
    private String description;
}