package com.example.shopping.service;

import com.example.shopping.controller.req.ProductCreateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
public interface ProductService{
    void productCreate(ProductCreateRequest productCreateRequest, List<MultipartFile> imgPaths) throws IOException;


}
