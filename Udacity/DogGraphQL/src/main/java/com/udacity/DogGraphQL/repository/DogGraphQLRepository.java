package com.udacity.DogGraphQL.entity.repository;

import com.udacity.DogGraphQL.entity.Dog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.Id;

public interface DogGraphQLRepository extends CrudRepository<Dog, Long> {

}
