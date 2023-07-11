package com.zoo.zoo.animal;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zoo.zoo.enclosure.Enclosure;

@Service
public class AnimalAssignmentValidator {

    public Boolean validateIfAvailableBySpace(Animal animal, Enclosure enclosure, int availableSpace) {
        return animal.getAmount() <= availableSpace || enclosure.getAnimals().isEmpty();
    }

    public Boolean validateIfAvailableByFoodType(Animal newAnimal, List<Animal> animals) {
        for (Animal animal : animals) {
            if (newAnimal.getFood() != animal.getFood()) {
                return false;
            }
        }

        return true;
    }

}
