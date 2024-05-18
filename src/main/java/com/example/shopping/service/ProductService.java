package com.example.shopping.service;

import com.example.shopping.controller.req.ProductCreateRequest;
import com.example.shopping.controller.res.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
public interface ProductService{

    //List<MultipartFile> imgPaths
    void productCreate(ProductCreateRequest productCreateRequest) throws IOException;

   // Page<ProductResponse> productFindAll(Pageable pageable);

    ProductResponse productDetailFind(Long productId);


}
