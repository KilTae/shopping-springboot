package com.example.shopping.repository;

import com.example.shopping.domain.Cart;
import com.example.shopping.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Page<Cart> findAllByMemberId(Long memberId, Pageable pageable);

    Optional<Cart> findByIdAndMember(Long cartId, Member member);


   // Optional<Cart> findByProductIdAAndMember(Long productId, Member member);

    List<Cart> findByMemberId(Long memberId);
}
