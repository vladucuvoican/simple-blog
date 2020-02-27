package com.wludio.blog.facade.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class SearchRequest {

    @NotNull(message = "Please provide a title")
    @Size(min = 3, max = 30, message = "The title should have between 3 and 30 characters")
    private String title;

    @NotEmpty(message = "The list of keywords should bot be empty")
    private List<String> keywords = new ArrayList<>();
}
