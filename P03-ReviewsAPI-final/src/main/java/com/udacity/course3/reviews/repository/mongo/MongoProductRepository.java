package com.udacity.course3.reviews.repository.mongo;

import com.udacity.course3.reviews.entity.mongo.ProductDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoProductRepository extends MongoRepository<ProductDocument,Integer> {

}
