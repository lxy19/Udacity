package com.udacity.course3.reviews.repository.mysql;

import com.udacity.course3.reviews.entity.mysql.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
    @Query("SELECT c FROM Comment c WHERE c.content = :content")
    Optional<Comment> findByContent(String content);
}
