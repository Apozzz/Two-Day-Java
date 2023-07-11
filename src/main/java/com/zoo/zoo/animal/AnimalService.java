package com.zoo.zoo.animal;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;

    public List<AnimalResponse> getAnimalsResponse() {
        return animalRepository.findAll().stream().map(animalMapper::toResponse).toList();
    }

    public List<Animal> getAnimals() {
        return animalRepository.findAll();
    }

    public void saveAnimal(AnimalRequest animalRequest) {
        Animal animal = animalMapper.fromRequest(animalRequest);
        animalRepository.save(animal);
    }

    public void deleteAnimal(Long id) {
        animalRepository.deleteById(id);
    }

    public void deleteAnimals() {
        animalRepository.deleteAll();
    }

    public List<Animal> getAnimalListFromAnimalListWrapper(AnimalListWrapper animalListWrapper) {
        List<AnimalRequest> requestAnimals = animalListWrapper.getAnimals();

        return requestAnimals.stream().map(animalMapper::fromRequest).toList();
    }

}
