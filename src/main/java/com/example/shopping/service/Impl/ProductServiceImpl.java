package com.example.shopping.service.Impl;

import com.example.shopping.controller.req.ProductCreateRequest;
import com.example.shopping.controller.res.ProductResponse;
import com.example.shopping.domain.Category;
import com.example.shopping.domain.Options;
import com.example.shopping.domain.Product;
import com.example.shopping.global.ErrorCode;
import com.example.shopping.global.exception.BusinessException;
import com.example.shopping.repository.*;
import com.example.shopping.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.example.shopping.domain.Member;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

   // @Value("${cloud.aws.s3.bucket}")
    //private String bucket;

    //private final S3Service
    private final ProductRepository productRepository;
   // private final ImageRepository imageRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final OptionRepository optionRepository;


    // 상품 등록 - 이미지 일단은 미포함으로 구현
    @Override
    public void productCreate(ProductCreateRequest productCreateRequest) {
        // List<MultipartFile> imgPath

        Member member = getMember();
        if(productRepository.findByName(productCreateRequest.getName()).isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATE_GOODS);
        }

        Category category = categoryRepository.findById(productCreateRequest.getCategoryId()).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_CATEGORY)
        );
        Product product = Product.create(productCreateRequest, category, member);
        productRepository.save(product);

        List<Options> optionsList = productCreateRequest.getOptionCreateRequest().stream().map(
                optionCreateRequest -> Options.toOption(optionCreateRequest, product)).collect(Collectors.toList());

        for(Options options : optionsList) {
            options.setProduct(product);
        }

        optionRepository.saveAll(optionsList);

        //s3

        //db저장

    }

    private Member getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return memberRepository.findByLoginId(authentication.getName()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));
    }


    // 상품 조회
    @Override
    @Transactional(readOnly = true)
    public ProductResponse productDetailFind(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_GOODS));
        return new ProductResponse(product);
    }


    // 상품 검색


}
