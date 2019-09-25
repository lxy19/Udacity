package com.udacity.DogGraphQL.entity.mutator;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.udacity.DogGraphQL.entity.Dog;
import com.udacity.DogGraphQL.entity.Exception.BreedNotFoundException;
import com.udacity.DogGraphQL.entity.Exception.DogNotFoundException;
import com.udacity.DogGraphQL.entity.repository.DogGraphQLRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class mutation implements GraphQLMutationResolver {
    private DogGraphQLRepository dogGraphQLRepository;

    public mutation(DogGraphQLRepository dogGraphQLRepository){
        this.dogGraphQLRepository = dogGraphQLRepository;
    }

    public boolean deleteDogBreed(String breed){
        boolean deleted = false;
        Iterable<Dog> dogs = dogGraphQLRepository.findAll();
        for(Dog dog:dogs) {
            if (dog.getBreed().equals(breed)) {
                dogGraphQLRepository.delete(dog);
                deleted = true;
            }
        };
        if (!deleted)
          throw new BreedNotFoundException("Not found the dog with the breed",breed);
        return deleted;
    }

    public Dog updateDogName(String newName, Long id){
        Optional<Dog> dogOptional = dogGraphQLRepository.findById(id);
        if (dogOptional.isPresent()){
            Dog dog = dogOptional.get();
            dog.setName(newName);
            dogGraphQLRepository.save(dog);
            return dog;
        }
        else
            throw new DogNotFoundException("Dog not found", id);
    }
}
