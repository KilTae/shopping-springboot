package com.example.shopping.controller;


import com.example.shopping.controller.req.ProductCreateRequest;
import com.example.shopping.controller.req.ProductEditRequest;
import com.example.shopping.controller.req.UpdateProductRequest;
import com.example.shopping.controller.res.ProductResponse;
import com.example.shopping.controller.res.UpdateProductResponse;
import com.example.shopping.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    // 상품 가격 변경 확인
    @GetMapping("/product/checkUpdateProduct")
    @ResponseStatus(HttpStatus.OK)
    // @ApiOperation(value = "상품 가격 변경 확인",notes = "changeCheck True = 변경 / False = 변경 없음")
    public List<UpdateProductResponse> checkProductUpdate(@RequestBody List<UpdateProductRequest> updateProductRequest) {
        return productService.checkProductUpdate(updateProductRequest);
    }

    @PostMapping("/product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('SELLER')")
    //  @ApiOperation(value = "상품 수정")
    public void productEdit(@PathVariable("productId") Long goodsId,
                            @RequestPart(value = "productEditRequest") @Valid ProductEditRequest productEditRequest,
                            @RequestPart(value = "multipartFileList") List<MultipartFile> multipartFiles) {

        productService.productEdit(goodsId, productEditRequest, multipartFiles);
    }

    // 상품 삭제
    @DeleteMapping("/product/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('SELLER')")
    // @ApiOperation(value = "상품 삭제")
    public void productDelete(@PathVariable("productId") Long productId) {
        productService.productDelete(productId);
    }

    @GetMapping("/product")
    @ResponseStatus(HttpStatus.OK)
    //@ApiOperation(value = "상품 전체 조회")
    public Page<ProductResponse> productFindAll(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return productService.productFindAll(pageable);
    }

    @GetMapping("/product/keyword")
    @ResponseStatus(HttpStatus.OK)
  //  @ApiOperation(value = "상품 검색")
    public Page<ProductResponse> productFindKeyword(@RequestParam(value = "keyword") String keyword,
                                                @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return productService.productFindKeyword(keyword, pageable);
    }

}
