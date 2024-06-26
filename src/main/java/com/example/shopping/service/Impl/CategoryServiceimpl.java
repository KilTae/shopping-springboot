package com.example.shopping.service.Impl;

import com.example.shopping.controller.req.CategoryCreateRequest;
import com.example.shopping.controller.req.CategoryEditRequest;
import com.example.shopping.controller.res.CategoryResponse;
import com.example.shopping.domain.Category;
import com.example.shopping.global.ErrorCode;
import com.example.shopping.global.exception.BusinessException;
import com.example.shopping.repository.CategoryRepository;
import com.example.shopping.repository.ProductRepository;
import com.example.shopping.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceimpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public void categoryCreate(CategoryCreateRequest categoryCreateRequest) {
        if(categoryRepository.findByCategory(categoryCreateRequest.getCategory()).isPresent()) {
            throw new BusinessException(ErrorCode.CATEGORY_NAME_DUPLICATED);
        }
        Category category = Category.toCategory(categoryCreateRequest);
        categoryRepository.save(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> categoryFindAll() {
        return categoryRepository.findAll()
                .stream().map(CategoryResponse::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void categoryEdit(Long categoryId, CategoryEditRequest categoryEditRequest) {
        Category category = ExistCategoryCheck(categoryId);
        category.editCategory(categoryEditRequest);
    }

    @Override
    public void categoryDelete(Long categoryId) {
        Category category = ExistCategoryCheck(categoryId);

        if(!productRepository.findAllByCategory(category).isEmpty()) {
            throw new BusinessException(ErrorCode.CATEGORY_EXIST_GOODS);
        }
        categoryRepository.delete(category);
    }

    private Category ExistCategoryCheck(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CATEGORY));
    }


}
