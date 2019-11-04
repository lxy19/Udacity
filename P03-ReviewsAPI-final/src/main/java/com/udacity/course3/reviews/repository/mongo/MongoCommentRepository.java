package com.udacity.course3.reviews.repository.mongo;

import com.udacity.course3.reviews.entity.mongo.CommentDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoCommentRepository extends MongoRepository<CommentDocument, Integer> {
    Optional<CommentDocument> findByCommentId(Integer id);
}
