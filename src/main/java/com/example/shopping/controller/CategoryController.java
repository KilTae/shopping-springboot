package com.example.shopping.controller;

import com.example.shopping.controller.req.CategoryCreateRequest;
import com.example.shopping.controller.res.CategoryResponse;
import com.example.shopping.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN')")
    //
    public void categoryCreate(@RequestBody @Valid CategoryCreateRequest categoryCreateRequest) {
        categoryService.categoryCreate(categoryCreateRequest);
    }

    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    //
    public List<CategoryResponse> categoryFindAll(){
        return categoryService.categoryFindAll();
    }

}
