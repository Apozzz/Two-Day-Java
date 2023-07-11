package com.zoo.zoo.enclosure;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zoo.zoo.animal.Animal;
import com.zoo.zoo.animal.AnimalNotFoundException;
import com.zoo.zoo.animal.AnimalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnclosureService {

    private final EnclosureRepository enclosureRepository;
    private final AnimalRepository animalRepository;
    private final EnclosureMapper enclosureMapper;

    public void addAnimalToEnclosure(Long enclosureId, Long animalId) {
        Enclosure enclosure = findEnclosureById(enclosureId);
        Animal animal = findAnimalById(animalId);
        enclosure.addAnimal(animal);
        enclosureRepository.save(enclosure);
    }

    public void removeAnimalFromEnclosure(Long enclosureId, Long animalId) {
        Enclosure enclosure = findEnclosureById(enclosureId);
        Animal animal = findAnimalById(animalId);
        enclosure.removeAnimal(animal);
        enclosureRepository.save(enclosure);
    }

    public void saveEnclosures(EnclosureListWrapper enclosureListWrapper) {
        List<EnclosureRequest> enclosureRequests = enclosureListWrapper.getEnclosures();
        List<Enclosure> enclosures = enclosureRequests.stream().map(enclosureMapper::fromRequest).toList();
        enclosureRepository.saveAll(enclosures);
    }

    public void saveEnclosures(List<Enclosure> enclosures) {
        enclosureRepository.saveAll(enclosures);
    }

    public void deleteEnclosures() {
        enclosureRepository.deleteAll();
    }

    public List<EnclosureResponse> getEnclosuresResponse() {
        return enclosureRepository.findAll().stream().map(enclosureMapper::toResponse).toList();
    }

    public List<Enclosure> getEnclosures() {
        return enclosureRepository.findAll();
    }

    private Enclosure findEnclosureById(Long enclosureId) {
        return enclosureRepository.findById(enclosureId)
                .orElseThrow(() -> new EnclosureNotFoundException("Enclosure not found with ID: " + enclosureId));
    }

    private Animal findAnimalById(Long animalId) {
        return animalRepository.findById(animalId)
                .orElseThrow(() -> new AnimalNotFoundException("Animal not found with ID: " + animalId));
    }

}
