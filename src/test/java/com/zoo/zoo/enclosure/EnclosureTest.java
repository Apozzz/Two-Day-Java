package com.zoo.zoo.enclosure;

import org.junit.Assert;
import org.junit.Test;

import com.zoo.zoo.animal.Animal;
import com.zoo.zoo.animal.FoodType;

import java.util.Arrays;
import java.util.List;

public class EnclosureTest {

        @Test
        public void testAddAnimal() {
                Enclosure enclosure = Enclosure.builder()
                                .id(1L)
                                .name("Enclosure 1")
                                .size(Size.SMALL)
                                .location(Location.INSIDE)
                                .objects(Arrays.asList("Tree", "Rock"))
                                .build();

                Animal animal = Animal.builder()
                                .id(1L)
                                .species("Lion")
                                .food(FoodType.CARNIVORE)
                                .amount(2)
                                .build();

                enclosure.addAnimal(animal);

                List<Animal> animals = enclosure.getAnimals();
                Assert.assertEquals(1, animals.size());
                Assert.assertEquals(animal, animals.get(0));
                Assert.assertEquals(enclosure, animal.getEnclosure());
        }

        @Test
        public void testRemoveAnimal() {
                Enclosure enclosure = Enclosure.builder()
                                .id(1L)
                                .name("Enclosure 1")
                                .size(Size.SMALL)
                                .location(Location.INSIDE)
                                .objects(Arrays.asList("Tree", "Rock"))
                                .build();

                Animal animal = Animal.builder()
                                .id(1L)
                                .species("Lion")
                                .food(FoodType.CARNIVORE)
                                .amount(2)
                                .enclosure(enclosure)
                                .build();

                enclosure.addAnimal(animal);
                enclosure.removeAnimal(animal);

                List<Animal> animals = enclosure.getAnimals();
                Assert.assertEquals(0, animals.size());
                Assert.assertNull(animal.getEnclosure());
        }

}
