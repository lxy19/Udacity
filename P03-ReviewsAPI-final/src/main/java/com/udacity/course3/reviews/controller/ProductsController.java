package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.mongo.CommentDocument;
import com.udacity.course3.reviews.entity.mongo.ProductDocument;
import com.udacity.course3.reviews.entity.mongo.ReviewDocument;
import com.udacity.course3.reviews.entity.mysql.Comment;
import com.udacity.course3.reviews.entity.mysql.Product;
import com.udacity.course3.reviews.entity.mysql.Review;
import com.udacity.course3.reviews.repository.mongo.MongoProductRepository;
import com.udacity.course3.reviews.repository.mysql.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with product entity.
 */
@RestController
@RequestMapping("/products")
public class ProductsController {

    private ProductRepository productRepository;
    private MongoProductRepository mongoProductRepository;

    /**
     * Creates a product.
     *
     * 1. Accept product as argument. Use {@link RequestBody} annotation.
     * 2. Save product.
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody Product product) {
        Optional<Product> productOptional = productRepository.findById(product.getProductId());
        if (productOptional.isPresent()) {
            List<ReviewDocument> reviewDocumentList = new ArrayList<>();
            for (Review review : product.getReviews()) {
                ReviewDocument reviewDocument = new ReviewDocument();
                reviewDocument.setReview_id(review.getReviewId());
                reviewDocument.setReview_description(review.getReviewDescription());
                List<CommentDocument> commentDocuments = new ArrayList<>();
                review.getComments().stream().forEach(comment -> {
                            CommentDocument commentDocument = new CommentDocument();
                            commentDocument.setContent(comment.getContent());
                            commentDocument.setCommentId(comment.getCommentId());
                            commentDocuments.add(commentDocument);
                });
                reviewDocument.setCommentList(commentDocuments);
                reviewDocumentList.add(reviewDocument);
            }

            ProductDocument productDocument = new ProductDocument();
            productDocument.setProduct_id(product.getProductId());
            productDocument.setProductName(product.getProductName());
            productDocument.setReviewList(reviewDocumentList);
            mongoProductRepository.save(productDocument);
        }
    }

    /**
     * Finds a product by id.
     *
     * @param id The id of the product.
     * @return The product if found, or a 404 not found.
     */
    @RequestMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Integer id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            ProductDocument productDocument = new ProductDocument();
            productDocument.setProduct_id(product.getProductId());
            productDocument.setProductName(product.getProductName());
            product.getReviews().forEach(review->{
                ReviewDocument reviewDocument = new ReviewDocument();
                reviewDocument.setReview_id(review.getReviewId());
                reviewDocument.setReview_description(review.getReviewDescription());
                review.getComments().forEach(comment->{
                    CommentDocument commentDocument = new CommentDocument();
                    commentDocument.setCommentId(comment.getCommentId());
                    commentDocument.setContent(comment.getContent());
                    reviewDocument.add(commentDocument);
                });
                productDocument.add(reviewDocument);
            });
            mongoProductRepository.save(productDocument);
            return new ResponseEntity<>(productOptional.get(), HttpStatus.CREATED);
        }
        else {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Lists all products.
     *
     * @return The list of products.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<?> listProducts() {
        return mongoProductRepository.findAll();
    }
}