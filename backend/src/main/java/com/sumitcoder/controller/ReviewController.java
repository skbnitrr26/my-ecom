package com.sumitcoder.controller;

import com.sumitcoder.exception.ProductException;
import com.sumitcoder.exception.ReviewNotFoundException;
import com.sumitcoder.exception.UserException;
import com.sumitcoder.model.Product;
import com.sumitcoder.model.Review;
import com.sumitcoder.model.User;
import com.sumitcoder.request.CreateReviewRequest;
import com.sumitcoder.response.ApiResponse;
import com.sumitcoder.service.ProductService;
import com.sumitcoder.service.ReviewService;
import com.sumitcoder.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<List<Review>> getReviewsByProductId(
            @PathVariable Long productId) {

        List<Review> reviews = reviewService.getReviewsByProductId(productId);
        return ResponseEntity.ok(reviews);

    }

    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<Review> writeReview(
            @RequestBody CreateReviewRequest req,
            @PathVariable Long productId,
            @RequestHeader("Authorization") String jwt) throws UserException, ProductException {

        User user = userService.findUserProfileByJwt(jwt);
        Product product = productService.findProductById(productId);

        Review review = reviewService.createReview(
                req, user, product
        );
        return ResponseEntity.ok(review);

    }

    @PatchMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> updateReview(
            @RequestBody CreateReviewRequest req,
            @PathVariable Long reviewId,
            @RequestHeader("Authorization") String jwt)
            throws UserException,
            ReviewNotFoundException, AuthenticationException {

        User user = userService.findUserProfileByJwt(jwt);

        Review review = reviewService.updateReview(
                reviewId,
                req.getReviewText(),
                req.getReviewRating(),
                user.getId()
        );
        return ResponseEntity.ok(review);

    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(
            @PathVariable Long reviewId,
            @RequestHeader("Authorization") String jwt) throws UserException,
            ReviewNotFoundException, AuthenticationException {

        User user = userService.findUserProfileByJwt(jwt);

        reviewService.deleteReview(reviewId, user.getId());
        ApiResponse res = new ApiResponse();
        res.setMessage("Review deleted successfully");
        res.setStatus(true);

        return ResponseEntity.ok(res);

    }
}
