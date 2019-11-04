package com.udacity.course3.reviews;

import com.udacity.course3.reviews.entity.mysql.Comment;
import com.udacity.course3.reviews.entity.mysql.Product;
import com.udacity.course3.reviews.entity.mysql.Review;
import com.udacity.course3.reviews.repository.mysql.CommentRepository;
import com.udacity.course3.reviews.repository.mysql.ProductRepository;
import com.udacity.course3.reviews.repository.mysql.ReviewRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReviewsJPATests {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private CommentRepository commentRepository;

    private Comment comment = new Comment();
    private Review review = new Review();
    private Product product = new Product();
    private List<Comment> comments = new ArrayList<>();
    private List<Review> reviewList = new ArrayList<>();

    @Before
    public void setUp() {
        comment.setContent("This is a positive feedback");
        comments.add(comment);
        commentRepository.save(comment);
        review.setComments(comments);
        review.setReviewDescription("This is about a product review");
        reviewList.add(review);
        product.setReviews(reviewList);
        product.setProductName("Sneakers");
    }

    @After
    public void after() {
        entityManager.close();
    }

    @Test
    public void injectedComponentsAreNotNull() {
        assertNotNull(dataSource);
        assertNotNull(entityManager);
        assertNotNull(testEntityManager);
        assertNotNull(productRepository);
        assertNotNull(reviewRepository);
        assertNotNull(commentRepository);
    }

    @Test
    public void testComment() {
        entityManager.persist(comment);
        Optional<Comment> actualComment = commentRepository.findByContent("This is a positive feedback");
        assertNotNull(actualComment.get());
        assertEquals(actualComment.get().getCommentId(), comment.getCommentId());
        assertEquals(actualComment.get().getContent(), comment.getContent());
        entityManager.remove(comment);
    }

    @Test
    public void testReview() {
        entityManager.persist(review);
        Review actualReview = reviewRepository.findAll().get(0);
        assertNotNull(actualReview);
        assertEquals(actualReview.getReviewId(), review.getReviewId());
        assertEquals(actualReview.getReviewDescription(), review.getReviewDescription());
        entityManager.remove(review);
    }

    @Test
    public void testProduct() {
        entityManager.persist(product);
        Optional<Product> actualProduct = productRepository.findById(1);
        assertNotNull(actualProduct.get());
        assertEquals(actualProduct.get().getProductId(), product.getProductId());
        assertEquals(actualProduct.get().getProductName(), product.getProductName());
        entityManager.remove(product);
    }
}