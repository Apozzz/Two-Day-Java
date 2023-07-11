package com.zoo.zoo.animal;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AnimalControllerTest {

    @Mock
    private AnimalService animalService;

    @Mock
    private AnimalAssignmentService animalAssignmentService;

    @InjectMocks
    private AnimalController animalController;

    @Test
    public void testGetAnimals() {
        List<AnimalResponse> animalResponses = Arrays.asList(
                new AnimalResponse("Lion", FoodType.CARNIVORE, 2),
                new AnimalResponse("Elephant", FoodType.HERBIVORE, 5));

        Mockito.when(animalService.getAnimalsResponse()).thenReturn(animalResponses);
        List<AnimalResponse> result = animalController.getAnimals();

        Assert.assertEquals(animalResponses, result);
        Mockito.verify(animalService, Mockito.times(1)).getAnimalsResponse();
    }

    @Test
    public void testSaveAnimals_Success() {
        AnimalListWrapper animalListWrapper = new AnimalListWrapper();
        Mockito.when(animalService.getAnimalListFromAnimalListWrapper(animalListWrapper))
                .thenReturn(Collections.emptyList());
        String result = animalController.saveAnimals(animalListWrapper);

        Assert.assertEquals("Animals were saved successfully!", result);
        Mockito.verify(animalService, Mockito.times(1)).getAnimalListFromAnimalListWrapper(animalListWrapper);
        Mockito.verify(animalAssignmentService, Mockito.times(1)).assignAnimalsToEnclosures(Collections.emptyList());
    }

    @Test
    public void testSaveAnimals_Exception() {
        AnimalListWrapper animalListWrapper = new AnimalListWrapper();
        Mockito.when(animalService.getAnimalListFromAnimalListWrapper(animalListWrapper))
                .thenThrow(new RuntimeException());
        String result = animalController.saveAnimals(animalListWrapper);

        Assert.assertEquals("Animals were not saved", result);
        Mockito.verify(animalService, Mockito.times(1)).getAnimalListFromAnimalListWrapper(animalListWrapper);
        Mockito.verify(animalAssignmentService, Mockito.times(0)).assignAnimalsToEnclosures(Mockito.anyList());
    }

    @Test
    public void testDeleteAnimals() {
        String result = animalController.deleteAnimals();

        Assert.assertEquals("Animals deleted successfully", result);
        Mockito.verify(animalService, Mockito.times(1)).deleteAnimals();
    }

}
