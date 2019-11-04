package com.udacity.course3.reviews.entity.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Document("products")
public class ProductDocument {
    @Id
    private int product_id;

    private String productName;

    private List<ReviewDocument> reviewList;

    public ProductDocument() {
        this.productName = "";
        this.reviewList = new ArrayList<>();
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<ReviewDocument> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<ReviewDocument> reviewList) {
        this.reviewList = reviewList;
    }

}
