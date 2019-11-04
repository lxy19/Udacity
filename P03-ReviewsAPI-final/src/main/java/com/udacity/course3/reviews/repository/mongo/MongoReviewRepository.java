package com.udacity.course3.reviews.repository.mongo;

import com.udacity.course3.reviews.entity.mongo.ReviewDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoReviewRepository extends MongoRepository<ReviewDocument, Integer> {
}
