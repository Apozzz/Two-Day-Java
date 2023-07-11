package com.zoo.zoo.animal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.github.benmanes.caffeine.cache.Cache;
import com.zoo.zoo.enclosure.Enclosure;
import com.zoo.zoo.enclosure.EnclosureService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnimalAssignmentService {

    private final EnclosureService enclosureService;
    private final AnimalAssignmentValidator animalAssignmentValidator;
    private final Cache<Enclosure, List<Animal>> animalCache;

    public void assignAnimalsToEnclosures(List<Animal> animals) throws AnimalAssignmentException {
        List<Enclosure> enclosures = enclosureService.getEnclosures();
        int animalsAmount = getAnimalsAmount(animals);
        int enclosureRatio = getEnclosureRatio(animalsAmount, enclosures);

        List<Animal> sortedAnimals = new ArrayList<>(animals);
        sortedAnimals.sort(Comparator.comparing(Animal::getFood, Comparator.reverseOrder())
                .thenComparing(Animal::getAmount, Comparator.reverseOrder()));

        List<Enclosure> sortedEnclosures = new ArrayList<>(enclosures);
        sortedEnclosures
                .sort(Comparator.comparing(enclosure -> enclosure.getSize().getValue(), Comparator.reverseOrder()));

        Map<Enclosure, Integer> enclosureSizes = getEnclosureSizesMap(sortedEnclosures, enclosureRatio);

        for (Animal animal : sortedAnimals) {
            List<Enclosure> availableEnclosures = determineAvailableEnclosuresForAnimal(animal, enclosureSizes);

            if (availableEnclosures.isEmpty()) {
                throw new AnimalAssignmentException("Animal could not be added");
            }

            for (Enclosure enclosure : availableEnclosures) {
                if (enclosureSizes.get(enclosure) == 0) {
                    enclosureSizes.remove(enclosure);
                }

                Integer index = sortedEnclosures.indexOf(enclosure);
                sortedEnclosures.set(index, enclosure);
            }
        }

        enclosureService.saveEnclosures(sortedEnclosures);
    }

    private List<Enclosure> determineAvailableEnclosuresForAnimal(Animal animal,
            Map<Enclosure, Integer> enclosureSizes) {
        List<Enclosure> availableEnclosures = new ArrayList<>();
        Integer animalAmount = animal.getAmount();

        for (Map.Entry<Enclosure, Integer> entry : enclosureSizes.entrySet()) {
            Enclosure enclosure = entry.getKey();
            Integer value = entry.getValue();
            List<Animal> cachedAnimals = animalCache.getIfPresent(enclosure);

            if (cachedAnimals == null) {
                cachedAnimals = saveEnclosureAnimalsToCache(enclosure);
            }

            if (Boolean.FALSE.equals(validateAnimal(animal, enclosure, value, cachedAnimals))) {
                continue;
            }

            Integer amountToTransfer = animalAmount < value ? animalAmount : value;

            if (animal.getFood() == FoodType.CARNIVORE && Objects.equals(animal.getAmount(), value)
                    && enclosure.getAnimals().isEmpty()) {

                saveAnimalToEnclosureIfItFitsPerfectly(animal, cachedAnimals, enclosureSizes, enclosure, value,
                        amountToTransfer, availableEnclosures);
                break;
            }

            if (animalAmount <= 0) {
                continue;
            }

            saveAnimalToEnclosureIfItsSizeIsTooBig(animal, cachedAnimals, enclosureSizes, enclosure, value,
                    amountToTransfer, availableEnclosures);
            animalAmount -= value;
        }

        return availableEnclosures;
    }

    private Boolean validateAnimal(Animal animal, Enclosure enclosure, Integer value, List<Animal> cachedAnimals) {
        if (Boolean.FALSE.equals(animalAssignmentValidator.validateIfAvailableBySpace(animal, enclosure, value))) {
            return false;
        }

        return animalAssignmentValidator.validateIfAvailableByFoodType(animal, cachedAnimals);
    }

    private Map<Enclosure, Integer> getEnclosureSizesMap(List<Enclosure> enclosures, int enclosureRatio) {
        Map<Enclosure, Integer> enclosureSizes = new LinkedHashMap<>();

        for (Enclosure enclosure : enclosures) {
            enclosureSizes.put(enclosure, enclosure.getSize().getValue() * enclosureRatio);
        }

        return enclosureSizes;
    }

    private int getEnclosureRatio(int animalsAmount, List<Enclosure> enclosures) {
        int enclosuresAmount = 0;

        for (Enclosure enclosure : enclosures) {
            enclosuresAmount += enclosure.getSize().getValue();
        }

        return enclosuresAmount == 0 ? enclosuresAmount : (int) Math.ceil((double) animalsAmount / enclosuresAmount);
    }

    private int getAnimalsAmount(List<Animal> animals) {
        int animalsAmount = 0;

        for (Animal animal : animals) {
            animalsAmount += animal.getAmount();
        }

        return animalsAmount;
    }

    private Animal getAnimalCopy(Animal animal) {
        return Animal.builder()
                .id(animal.getId())
                .species(animal.getSpecies())
                .food(animal.getFood())
                .amount(animal.getAmount())
                .build();
    }

    private void saveAnimal(Animal animal, List<Animal> cachedAnimals, Map<Enclosure, Integer> enclosureSizes,
            Enclosure enclosure, Integer value, Integer amountToTransfer) {
        cachedAnimals.add(animal);
        animalCache.put(enclosure, cachedAnimals);
        enclosureSizes.put(enclosure, value - amountToTransfer);
        enclosure.addAnimal(animal);
    }

    private void removeAnimalsFromPreviousEnclosures(List<Enclosure> enclosures, Animal animal) {
        for (Enclosure enclosure : enclosures) {
            enclosure.removeAnimalBySpecies(animal.getSpecies());
        }
    }

    private List<Animal> saveEnclosureAnimalsToCache(Enclosure enclosure) {
        List<Animal> animals = enclosure.getAnimals();
        animalCache.put(enclosure, animals);

        return animals;
    }

    private void saveAnimalToEnclosureIfItFitsPerfectly(Animal animal, List<Animal> cachedAnimals,
            Map<Enclosure, Integer> enclosureSizes,
            Enclosure enclosure, Integer value, Integer amountToTransfer, List<Enclosure> availableEnclosures) {
        saveAnimal(animal, cachedAnimals, enclosureSizes, enclosure, value, amountToTransfer);
        removeAnimalsFromPreviousEnclosures(availableEnclosures, animal);
        availableEnclosures.clear();
        availableEnclosures.add(enclosure);
    }

    private void saveAnimalToEnclosureIfItsSizeIsTooBig(Animal animal, List<Animal> cachedAnimals,
            Map<Enclosure, Integer> enclosureSizes,
            Enclosure enclosure, Integer value, Integer amountToTransfer, List<Enclosure> availableEnclosures) {
        Animal copiedAnimal = getAnimalCopy(animal);
        copiedAnimal.setAmount(amountToTransfer);
        saveAnimal(copiedAnimal, cachedAnimals, enclosureSizes, enclosure, value, amountToTransfer);
        availableEnclosures.add(enclosure);
    }

}