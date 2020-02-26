package com.wludio.blog.facade;


import com.wludio.blog.entites.Article;
import com.wludio.blog.facade.dto.SearchRequest;
import com.wludio.blog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FacadeArticleService {

    private final ArticleService articleService;

    public List<Article> findBySearchRequest(SearchRequest searchRequest, Pageable pageable) {

//        ExampleMatcher matcher = ExampleMatcher
//                .matchingAll()
//                .withMatcher("title", contains().ignoreCase());
        // TODO change this method to query by keywords a well in a dynamic way
        return articleService.findByTitle(searchRequest.getTitle(), pageable);
    }
}
