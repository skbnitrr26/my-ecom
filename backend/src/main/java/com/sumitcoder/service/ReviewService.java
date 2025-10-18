package com.sumitcoder.service;

import com.sumitcoder.exception.ReviewNotFoundException;
import com.sumitcoder.model.Product;
import com.sumitcoder.model.Review;
import com.sumitcoder.model.User;
import com.sumitcoder.request.CreateReviewRequest;

import javax.naming.AuthenticationException;
import java.util.List;

public interface ReviewService {

    Review createReview(CreateReviewRequest req,
                        User user,
                        Product product);

    List<Review> getReviewsByProductId(Long productId);

    Review updateReview(Long reviewId,
                        String reviewText,
                        double rating,
                        Long userId) throws ReviewNotFoundException, AuthenticationException;


    void deleteReview(Long reviewId, Long userId) throws ReviewNotFoundException, AuthenticationException;

}
