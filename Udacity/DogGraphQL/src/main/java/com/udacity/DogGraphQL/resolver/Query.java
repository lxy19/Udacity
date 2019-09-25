package com.udacity.DogGraphQL.entity.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.udacity.DogGraphQL.entity.Dog;
import com.udacity.DogGraphQL.entity.Exception.DogNotFoundException;
import com.udacity.DogGraphQL.entity.repository.DogGraphQLRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class Query implements GraphQLQueryResolver {
    private DogGraphQLRepository dogGraphQLRepository;

    public Query(DogGraphQLRepository dogGraphQLRepository){
        this.dogGraphQLRepository = dogGraphQLRepository;
    }

    public Iterable<Dog> findAll(){
        return dogGraphQLRepository.findAll();
    }

    public Iterable<Dog> findDogs(){
        return findAll();
    }

    public List<String> findDogBreeds(){
        List<String> list = new ArrayList<>();
        findAll().forEach(dog->list.add(dog.getBreed()));
        return list;
    }

    public List<String> findDogNames(){
        List<String> list = new ArrayList<>();
        findAll().forEach(dog->list.add(dog.getName()));
        return list;
    }

    public Dog findDogById(long id){
        Optional<Dog> dogOptional = dogGraphQLRepository.findById(id);
        if (dogOptional.isPresent()){
            return dogOptional.get();
        }
        else
            throw new DogNotFoundException("DogNotFound",id);
    }
}
