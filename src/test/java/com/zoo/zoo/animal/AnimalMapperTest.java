package com.zoo.zoo.animal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AnimalMapperTest {

    private AnimalMapper animalMapper;

    @Before
    public void setup() {
        FoodTypeEnumConverter foodTypeEnumConverter = new FoodTypeEnumConverter();
        animalMapper = new AnimalMapper(foodTypeEnumConverter);
    }

    @Test
    public void testFromRequest() {
        AnimalRequest request = new AnimalRequest("Lion", "CARNIVORE", 2);
        Animal result = animalMapper.fromRequest(request);

        Assert.assertEquals("Lion", result.getSpecies());
        Assert.assertEquals(FoodType.CARNIVORE, result.getFood());
        Assert.assertEquals(Integer.valueOf(2), result.getAmount());
    }

    @Test
    public void testToResponse() {
        Animal animal = new Animal(null, "Lion", FoodType.CARNIVORE, 2, null);
        AnimalResponse result = animalMapper.toResponse(animal);

        Assert.assertEquals("Lion", result.getSpecies());
        Assert.assertEquals(FoodType.CARNIVORE, result.getFood());
        Assert.assertEquals(Integer.valueOf(2), result.getAmount());
    }

    @Test
    public void testToDTO() {
        Animal animal = new Animal(null, "Lion", FoodType.CARNIVORE, 2, null);
        AnimalDTO result = animalMapper.toDTO(animal);

        Assert.assertEquals("Lion", result.species());
        Assert.assertEquals(FoodType.CARNIVORE, result.food());
        Assert.assertEquals(Integer.valueOf(2), result.amount());
    }

}
