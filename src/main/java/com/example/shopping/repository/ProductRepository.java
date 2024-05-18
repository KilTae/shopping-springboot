package com.example.shopping.repository;

import com.example.shopping.domain.Category;
import com.example.shopping.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    Optional<Product> findByName(String productName);
    Page<Product> findProductByNameContaining(Pageable pageable, String keyword);
    List<Product> findAllByCategory(Category category);
    List<Product> findAllByMemberId(Long memberId);
}
