package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.mongo.CommentDocument;
import com.udacity.course3.reviews.entity.mongo.ProductDocument;
import com.udacity.course3.reviews.entity.mongo.ReviewDocument;
import com.udacity.course3.reviews.entity.mysql.Comment;
import com.udacity.course3.reviews.entity.mysql.Product;
import com.udacity.course3.reviews.entity.mysql.Review;
import com.udacity.course3.reviews.repository.mongo.MongoProductRepository;
import com.udacity.course3.reviews.repository.mongo.MongoReviewRepository;
import com.udacity.course3.reviews.repository.mysql.ProductRepository;
import com.udacity.course3.reviews.repository.mysql.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with review entity.
 */
@RestController
public class ReviewsController {

    private MongoReviewRepository mongoReviewRepository;
    private ProductRepository productRepository;
    private ReviewRepository reviewRepository;

    /**
     * Creates a review for a product.
     * <p>
     * 1. Add argument for review entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of product.
     * 3. If product not found, return NOT_FOUND.
     * 4. If found, save review.
     *
     * @param productId The id of the product.
     * @return The created review or 404 if product id is not found.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    public ResponseEntity<?> createReviewForProduct(@RequestBody Review review, @PathVariable("productId") Integer productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()){
            Product product = productOptional.get();
            ReviewDocument reviewDocument = new ReviewDocument();
            reviewDocument.setReview_id(review.getReviewId());
            reviewDocument.setReview_description(review.getReviewDescription());
            for(Comment comment: review.getComments()){
                CommentDocument commentDocument = new CommentDocument();
                commentDocument.setContent(comment.getContent());
                commentDocument.setCommentId(comment.getCommentId());
                reviewDocument.add(commentDocument);
            }
            mongoReviewRepository.save(reviewDocument);
            return new ResponseEntity<>(productOptional.get(),HttpStatus.CREATED);
        }
        else {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listReviewsForProduct(@PathVariable("productId") Integer productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()){
            return new ResponseEntity<>(productOptional.get().getReviews(), HttpStatus.OK);
        }
        else {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }
    }
}