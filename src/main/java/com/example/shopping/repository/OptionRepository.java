package com.example.shopping.repository;

import com.example.shopping.domain.Options;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OptionRepository extends JpaRepository<Options, Long> {
    List<Options> findByProductId(Long id);

    Optional<Options> findByIdAndProductId(Long optionId, Long productId);

}
