package com.udacity.course3.reviews.entity;

import javax.persistence.*;

@Entity(name="Comment")
@Table(name="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private int commentId;

    @Column(name="content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    public int getCommentId() {
        return commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
