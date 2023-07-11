package com.zoo.zoo.animal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AnimalServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private AnimalMapper animalMapper;

    @InjectMocks
    private AnimalService animalService;

    @Test
    public void testGetAnimalsResponse() {
        List<Animal> animals = Arrays.asList(
                new Animal(null, "Lion", FoodType.CARNIVORE, 2, null),
                new Animal(null, "Elephant", FoodType.HERBIVORE, 5, null));
        List<AnimalResponse> animalResponses = Arrays.asList(
                new AnimalResponse("Lion", FoodType.CARNIVORE, 2),
                new AnimalResponse("Elephant", FoodType.HERBIVORE, 5));

        Mockito.when(animalRepository.findAll()).thenReturn(animals);
        Mockito.when(animalMapper.toResponse(Mockito.any(Animal.class))).thenReturn(animalResponses.get(0),
                animalResponses.get(1));
        List<AnimalResponse> result = animalService.getAnimalsResponse();

        Assert.assertEquals(animalResponses, result);
        Mockito.verify(animalRepository, Mockito.times(1)).findAll();
        Mockito.verify(animalMapper, Mockito.times(2)).toResponse(Mockito.any(Animal.class));
    }

    @Test
    public void testGetAnimals() {
        List<Animal> animals = Arrays.asList(
                new Animal(null, "Lion", FoodType.CARNIVORE, 2, null),
                new Animal(null, "Elephant", FoodType.HERBIVORE, 5, null));

        Mockito.when(animalRepository.findAll()).thenReturn(animals);
        List<Animal> result = animalService.getAnimals();

        Assert.assertEquals(animals, result);
        Mockito.verify(animalRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testSaveAnimal() {
        AnimalRequest animalRequest = new AnimalRequest("Lion", "CARNIVORE", 2);
        Animal animal = new Animal(null, "Lion", FoodType.CARNIVORE, 2, null);

        Mockito.when(animalMapper.fromRequest(animalRequest)).thenReturn(animal);
        animalService.saveAnimal(animalRequest);

        Mockito.verify(animalRepository, Mockito.times(1)).save(animal);
    }

    @Test
    public void testDeleteAnimal() {
        Long animalId = 1L;

        animalService.deleteAnimal(animalId);

        Mockito.verify(animalRepository, Mockito.times(1)).deleteById(animalId);
    }

    @Test
    public void testDeleteAnimals() {
        animalService.deleteAnimals();

        Mockito.verify(animalRepository, Mockito.times(1)).deleteAll();
    }

    @Test
    public void testGetAnimalListFromAnimalListWrapper() {
        AnimalListWrapper animalListWrapper = new AnimalListWrapper();
        List<AnimalRequest> requestAnimals = Arrays.asList(
                new AnimalRequest("Lion", "CARNIVORE", 2),
                new AnimalRequest("Elephant", "HERBIVORE", 5));

        animalListWrapper.setAnimals(requestAnimals);
        Mockito.when(animalMapper.fromRequest(Mockito.any(AnimalRequest.class))).thenReturn(
                new Animal(null, "Lion", FoodType.CARNIVORE, 2, null),
                new Animal(null, "Elephant", FoodType.HERBIVORE, 5, null));

        List<Animal> result = animalService.getAnimalListFromAnimalListWrapper(animalListWrapper);

        Assert.assertEquals(2, result.size());
        Mockito.verify(animalMapper, Mockito.times(2)).fromRequest(Mockito.any(AnimalRequest.class));
    }

}
