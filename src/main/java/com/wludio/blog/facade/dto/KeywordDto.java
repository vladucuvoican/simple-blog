package com.wludio.blog.facade.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class KeywordDto {

    private Long id;

    @NotNull(message = "Please provide a name")
    @Size(min = 3, max = 30, message = "The name should have between 3 and 30 characters")
    private String name;
}
