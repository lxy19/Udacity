package com.udacity.course3.reviews.entity;

import javax.persistence.*;
import java.util.List;

@Entity(name="Product")
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Integer productId;
    @Column(name="product_name")
    private String productName;

    public Integer getProductId() {
        return productId;
    }

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private List<Review> reviews;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
