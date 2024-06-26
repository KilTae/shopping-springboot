package com.example.shopping.service;

import com.example.shopping.controller.req.CategoryCreateRequest;
import com.example.shopping.controller.req.CategoryEditRequest;
import com.example.shopping.controller.res.CategoryResponse;

import java.util.List;
public interface CategoryService {

    void categoryCreate(CategoryCreateRequest categoryCreateRequest);
    List<CategoryResponse> categoryFindAll();
    void categoryEdit(Long categoryId, CategoryEditRequest categoryEditRequest);

    void categoryDelete(Long categoryId);
}
