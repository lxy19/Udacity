package com.udacity.course3.reviews.entity.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Document("reviews")
public class ReviewDocument {
    @Id
    private int review_id;

    private String review_description;

    private List<CommentDocument> commentList;

    public ReviewDocument() {
        this.review_description = "";
        this.commentList = new ArrayList<>();
    }

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {
        this.review_id = review_id;
    }

    public String getReview_description() {
        return review_description;
    }

    public void setReview_description(String review_description) {
        this.review_description = review_description;
    }

    public List<CommentDocument> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentDocument> commentList) {
        this.commentList = commentList;
    }

}
