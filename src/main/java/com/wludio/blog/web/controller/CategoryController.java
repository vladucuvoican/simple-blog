package com.wludio.blog.web.controller;

import com.wludio.blog.facade.FacadeCategoryService;
import com.wludio.blog.facade.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryController {

    private final FacadeCategoryService facadeCategoryService;

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoryDto>> getCategories(@RequestParam(defaultValue = "0") final Integer pageNo,
                                                           @RequestParam(defaultValue = "10") final Integer pageSize,
                                                           @RequestParam(defaultValue = "id") final String sortBy) {

        log.info("Request to getCategories");
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        List<CategoryDto> categories = facadeCategoryService.findAll(pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categories);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable @Min(1) final Long id) {

        log.info("Request to getCategoryById : {}", id);

        Optional<CategoryDto> category = facadeCategoryService.findById(id);

        if (category.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(category.get());
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody final CategoryDto category) {
        log.info("Request to create category: {}", category);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(facadeCategoryService.create(category));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable @Min(1) final Long id) {
        log.info("Request to delete category with the id : {}", id);

        facadeCategoryService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> update(@PathVariable @Min(1) final Long id,
                                              @Valid @RequestBody final CategoryDto category) {
        log.info("Request to update category with the id: {} and info: {} ", id, category);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(facadeCategoryService.update(id, category));
    }
}
