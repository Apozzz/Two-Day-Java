package com.zoo.zoo.animal;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;
    private final AnimalAssignmentService animalAssignmentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AnimalResponse> getAnimals() {
        return animalService.getAnimalsResponse();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String saveAnimals(@RequestBody AnimalListWrapper animalListWrapper) {
        try {
            List<Animal> animals = animalService.getAnimalListFromAnimalListWrapper(animalListWrapper);
            animalAssignmentService.assignAnimalsToEnclosures(animals);

            return "Animals were saved successfully!";
        } catch (Exception e) {
            e.printStackTrace();

            return "Animals were not saved";
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public String deleteAnimals() {
        animalService.deleteAnimals();

        return "Animals deleted successfully";
    }

}
