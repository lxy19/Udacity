package com.udacity.course3.reviews.repository.mysql;

import com.udacity.course3.reviews.entity.mysql.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
    @Query("select r from Review r")
    List<Review> findAll();
}
