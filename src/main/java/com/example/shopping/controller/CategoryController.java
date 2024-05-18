package com.example.shopping.controller;

import com.example.shopping.controller.req.CategoryCreateRequest;
import com.example.shopping.controller.req.CategoryEditRequest;
import com.example.shopping.controller.res.CategoryResponse;
import com.example.shopping.domain.Category;
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

    @PutMapping("/categories/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN')")
    //
    public void categoryEdit(@PathVariable("categoryId") Long categoryId, @RequestBody CategoryEditRequest categoryEditRequest) {
        categoryService.categoryEdit(categoryId, categoryEditRequest);
    }

    @DeleteMapping("/categories/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN')")
    //
    public void categoryDelete(@PathVariable("categoryId") Long categoryId){
        categoryService.categoryDelete(categoryId);
    }




}
