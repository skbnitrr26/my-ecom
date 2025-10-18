package com.sumitcoder.repository;

import com.sumitcoder.model.Product;
import com.sumitcoder.model.Review;
import com.sumitcoder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findReviewsByUserId(Long userId);
    List<Review> findReviewsByProductId(Long productId);
}
