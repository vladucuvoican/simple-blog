package com.wludio.blog.facade.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchRequest {

    String title;
    List<String> keywords;
}
