package com.udacity.course3.reviews.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name="Review")
@Table(name="reviews")
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_id")
    private int reviewId;
    @Column(name="review_description")
    private String reviewDescription;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @OneToMany(mappedBy="review", cascade = CascadeType.PERSIST)
    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getReviewDescription() {
        return reviewDescription;
    }

    public void setReviewDescription(String reviewDescription) {
        this.reviewDescription = reviewDescription;
    }

    public int getReviewId() {
        return reviewId;
    }
}
