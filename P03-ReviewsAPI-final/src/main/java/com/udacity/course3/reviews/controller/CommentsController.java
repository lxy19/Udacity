package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.mongo.CommentDocument;
import com.udacity.course3.reviews.entity.mongo.ReviewDocument;
import com.udacity.course3.reviews.entity.mysql.Comment;
import com.udacity.course3.reviews.entity.mysql.Review;
import com.udacity.course3.reviews.repository.mongo.MongoCommentRepository;
import com.udacity.course3.reviews.repository.mongo.MongoReviewRepository;
import com.udacity.course3.reviews.repository.mysql.CommentRepository;
import com.udacity.course3.reviews.repository.mysql.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with comment entity.
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {
    private CommentRepository commentRepository;
    private ReviewRepository reviewRepository;
    private MongoReviewRepository mongoReviewRepository;

    /**
     * Creates a comment for a review.
     *
     * 1. Add argument for comment entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, save comment.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.POST)
    public ResponseEntity<?> createCommentForReview(@RequestBody Comment comment, @PathVariable("reviewId") Integer reviewId) {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (reviewOptional.isPresent()) {
            Review review = reviewOptional.get();
            comment.setReview(review);
            commentRepository.save(comment);

            Optional<ReviewDocument> reviewDocumentOptional = mongoReviewRepository.findById(reviewId);
            if (reviewDocumentOptional.isPresent()) {
                CommentDocument commentDocument = new CommentDocument();
                commentDocument.setCommentId(comment.getCommentId());
                commentDocument.setContent(comment.getContent());

                ReviewDocument reviewDocument = new ReviewDocument();
                reviewDocument.add(commentDocument);
                mongoReviewRepository.save(reviewDocument);
            }
            return new ResponseEntity<>(comment, HttpStatus.CREATED);
        }
        else {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * List comments for a review.
     *
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, return list of comments.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.GET)
    public List<?> listCommentsForReview(@PathVariable("reviewId") Integer reviewId) {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (reviewOptional.isPresent()){
            return reviewOptional.get().getComments();
        }
        else {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }
    }
}