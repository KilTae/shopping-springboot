package com.example.shopping.repository;

import com.example.shopping.domain.Product;
import com.example.shopping.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r join r.comment rc")
    Page<Review> findAllByProduct(Product product, Pageable pageable)
}
