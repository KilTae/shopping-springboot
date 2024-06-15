package com.example.shopping.service.Impl;

import com.example.shopping.controller.req.*;
import com.example.shopping.controller.res.ProductResponse;
import com.example.shopping.controller.res.UpdateProductResponse;
import com.example.shopping.domain.*;
import com.example.shopping.global.ErrorCode;
import com.example.shopping.global.exception.BusinessException;
import com.example.shopping.repository.*;
import com.example.shopping.service.ProductService;
import com.example.shopping.service.S3Service;
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


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final S3Service s3Service;
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final OptionRepository optionRepository;


    // 상품 등록
    @Override
    public void productCreate(ProductCreateRequest productCreateRequest, List<MultipartFile> imgPath) {


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
        List<String> list = s3Service.upload(imgPath);

        List<Image> imageList = list.stream().map(img -> Image.builder().fileUrl(img).product(product).build()).collect(Collectors.toList());
        for(Image image : imageList ) {
            image.setProduct(product);
        }
        imageRepository.saveAll(imageList);

        //db저장

    }

    // 상품 전체 검색

    @Override
    //@TimerAop 시간측정, aop에 관련해서 찾아보기
    @Transactional(readOnly = true)
  //  @Cacheable(cacheNames = "product", key = "#pageable")
    public Page<ProductResponse> productFindAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);

        Page<ProductResponse> responses = products.map(product -> new ProductResponse(product));
        return responses;

    }

    // 상품 검색 ( 키워드 )
    @Override
   // @TimerAop
    @Transactional(readOnly = true)
    public Page<ProductResponse> productFindKeyword(String keyword, Pageable pageable) {
        // keyword 로 검색 후 모든 상품 찾기
        Page<Product> product = productRepository.findProductByNameContaining(pageable, keyword);

        return product.map(ProductResponse::new);
    }


    //상품 가격 변경 확인
    @Transactional(readOnly = true)
    public List<UpdateProductResponse> checkProductUpdate(List<UpdateProductRequest> updateProductRequest) {
        List<UpdateProductResponse> productsList = new ArrayList<>();
        for(UpdateProductRequest request : updateProductRequest) {
            Product product = productRepository.findById(request.getProductId()).orElseThrow(
                    () -> new BusinessException(ErrorCode.NOT_FOUND_GOODS));
            if(product.getOptions().isEmpty()) {
                if(request.getOptionId() != null)
                    throw new BusinessException(ErrorCode.NOT_FOUND_OPTION);
                UpdateProductResponse productResponse = UpdateProductResponse.builder()
                        .productId(request.getProductId()).productPrice(request.getProductPrice()).changeCheck(false).build();

                if(product.getPrice() != request.getProductPrice())
                    productResponse.setChangeCheck(true);
                productsList.add(productResponse);
                return productsList;
            }

            Options options = optionRepository.findByIdAndProductId(request.getOptionId(), request.getProductId()).orElseThrow(
                    () -> new BusinessException(ErrorCode.NOT_FOUND_OPTION));
            UpdateProductResponse updateProductResponse = UpdateProductResponse.builder()
                    .productId(request.getProductId()).productPrice(options.getTotalPrice()).changeCheck(false).build();

            if(options.getTotalPrice() != request.getProductPrice())
                updateProductResponse.setChangeCheck(true);
            productsList.add(updateProductResponse);
        }

        return productsList;
    }

    @Override
    public void productEdit(Long productId, ProductEditRequest productEditRequest, List<MultipartFile> imgPaths) {
        Member member = getMember();

        if(productRepository.findByName(productEditRequest.getName()).isPresent())
            throw new BusinessException(ErrorCode.DUPLICATE_GOODS);

        Product product = productRepository.findById(productId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_GOODS));

        if(!product.getMemberId().equals(member.getId())) //refac
            throw new BusinessException(ErrorCode.NOT_SELLING_GOODS);

        product.update(productEditRequest);

        List<Options> options = optionRepository.findByProductId(productId);
        optionRepository.deleteAll(options);

        if(!productEditRequest.getOptionCreateRequest().isEmpty() && productEditRequest.getGoodsDescription() != null) {
            List<OptionCreateRequest> optionCreateRequestList = productEditRequest.getOptionCreateRequest();

            List<Options> optionsList = optionCreateRequestList.stream().map(optionCreateRequest -> Options.toOption(optionCreateRequest, product)).collect(Collectors.toList());
            for(Options option : optionsList) {
                option.setProduct(product);
            }

            optionRepository.saveAll(optionsList);

            List<Image> imageList = imageRepository.findByProductId(product.getId());
            for (Image image : imageList) {
                String fileName = image.getFileUrl();
                s3Service.deleteFile(fileName);
                imageRepository.deleteById(image.getId());
            }

            // S3 이미지 저장
            List<String> list = s3Service.upload(imgPaths);

            // 이미지 정보 저장
            List<Image> images = list.stream().map(img -> Image.builder().fileUrl(img).product(product).build()).collect(Collectors.toList());
            for (Image image : images) {
                image.setProduct(product);
            }
            imageRepository.saveAll(images);
        }
    }

    private Member getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return memberRepository.findByLoginId(authentication.getName()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));
    }

    @Override
    public void productDelete(Long goodsId) { // 204 error ? refac

        Member member = getMember();

        Product product = productRepository.findById(goodsId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_GOODS));
        // 상품 회원과 로그인한 회원이 다르면 예외처리
        if (!product.getMemberId().equals(member.getId()))
            throw new BusinessException(ErrorCode.NOT_SELLING_GOODS);

        // s3 이미지 삭제
        List<Image> imageList = imageRepository.findByProductId(product.getId());

        for (Image image : imageList) {
            String fileName = image.getFileUrl();
            s3Service.deleteFile(fileName);
        }
        productRepository.deleteById(product.getId());
    }

    // 상품 조회
    @Override
    @Transactional(readOnly = true)
  //  @Cacheable(cacheNames = "product", key = "#productId")
    public ProductResponse productDetailFind(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_GOODS));
        return new ProductResponse(product);
    }

/* jpa 쿼리 공부 후 refac
    @Override
    public Page<ProductResponse> searchBetweenPrice(ProductSearchCondition condition, Pageable pageable) {
        ProductSearchCondition result =
                new ProductSearchCondition(condition.getPriceMin(), condition.getPriceMax(), condition.getCategory());

        return productRepository.searchBetweenPrice(result, pageable);

    }
*/

    // 상품 검색


}
