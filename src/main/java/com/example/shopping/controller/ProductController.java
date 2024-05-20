package com.example.shopping.controller;


import com.example.shopping.controller.req.ProductCreateRequest;
import com.example.shopping.controller.res.ProductResponse;
import com.example.shopping.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    @PostMapping(value = "/product")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('SELLER')")
    //
    //, @RequestPart List<MultipartFile> multipartFileList
    public void productCreate(@RequestPart(value = "productCreateRequest") @Valid ProductCreateRequest productCreateRequest,
                              @RequestPart(value = "multipartFileList") List<MultipartFile> multipartFileList) throws IOException {
        productService.productCreate(productCreateRequest, multipartFileList);

    }

    @GetMapping("product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    //
    public ProductResponse productDetailFind(@PathVariable("productId") Long proudctId) {
        return productService.productDetailFind(proudctId);
    }
}
