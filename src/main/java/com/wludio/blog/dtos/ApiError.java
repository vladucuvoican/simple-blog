package com.wludio.blog.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApiError {
    private String message;
    private List<String> messages;
}
