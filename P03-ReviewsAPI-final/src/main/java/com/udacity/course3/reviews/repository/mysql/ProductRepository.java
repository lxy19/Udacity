package com.udacity.course3.reviews.repository.mysql;

import com.udacity.course3.reviews.entity.mysql.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
