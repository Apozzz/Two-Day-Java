package com.zoo.zoo.enclosure;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/enclosures")
@RequiredArgsConstructor
public class EnclosureController {

    private final EnclosureService enclosureService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EnclosureResponse> getEnclosures() {
        return enclosureService.getEnclosuresResponse();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String saveEnclosures(@RequestBody EnclosureListWrapper enclosureListWrapper) {
        try {
            enclosureService.saveEnclosures(enclosureListWrapper);

            return "Enclosures were saved successfully!";
        } catch (Exception e) {
            e.printStackTrace();

            return "Enclosures were not saved";
        }
    }

    @PostMapping("/{enclosureId}/animals/{animalId}")
    @ResponseStatus(HttpStatus.CREATED)
    public String addAnimalToEnclosure(@PathVariable Long enclosureId, @PathVariable Long animalId) {
        enclosureService.addAnimalToEnclosure(enclosureId, animalId);

        return "Animal added to enclosure successfully";
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public String deleteEnclosures() {
        enclosureService.deleteEnclosures();

        return "Enclosures deleted successfully";
    }

    @DeleteMapping("/{enclosureId}/animals/{animalId}")
    @ResponseStatus(HttpStatus.OK)
    public String removeAnimalFromEnclosure(@PathVariable Long enclosureId, @PathVariable Long animalId) {
        enclosureService.removeAnimalFromEnclosure(enclosureId, animalId);

        return "Animal removed from enclosure successfully";
    }

}
