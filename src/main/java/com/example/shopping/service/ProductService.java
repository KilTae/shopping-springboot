package com.example.shopping.service;

import com.example.shopping.controller.req.ProductCreateRequest;
import com.example.shopping.controller.req.ProductEditRequest;
import com.example.shopping.controller.req.UpdateProductRequest;
import com.example.shopping.controller.res.ProductResponse;
import com.example.shopping.controller.res.UpdateProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
public interface ProductService{


    void productCreate(ProductCreateRequest productCreateRequest, List<MultipartFile> imgPaths) throws IOException;

    Page<ProductResponse> productFindAll(Pageable pageable);

    Page<ProductResponse> productFindKeyword(String keyword, Pageable pageable);

    void productDelete(Long productId);

    List<UpdateProductResponse> checkProductUpdate(List<UpdateProductRequest> updateCheckRequest);

    void productEdit(Long goodsId, ProductEditRequest goodsEditRequest, List<MultipartFile> imgPaths);

    ProductResponse productDetailFind(Long productId);


}
