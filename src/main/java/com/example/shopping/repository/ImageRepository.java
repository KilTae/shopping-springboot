package com.example.shopping.repository;
import com.example.shopping.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByProductId(Long productId);
}
