package com.zoo.zoo.animal;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnimalMapper {

    private final FoodTypeEnumConverter foodTypeEnumConverter;

    public Animal fromRequest(AnimalRequest animalRequest) {
        return Animal.builder()
                .species(animalRequest.species())
                .food(foodTypeEnumConverter.convert(animalRequest.food()))
                .amount(animalRequest.amount())
                .build();
    }

    public AnimalResponse toResponse(Animal animal) {
        return AnimalResponse.builder()
                .species(animal.getSpecies())
                .food(animal.getFood())
                .amount(animal.getAmount())
                .build();
    }

    public AnimalDTO toDTO(Animal animal) {
        return AnimalDTO.builder()
                .species(animal.getSpecies())
                .food(animal.getFood())
                .amount(animal.getAmount())
                .build();
    }

}
