package com.zoo.zoo.enclosure;

import org.springframework.stereotype.Service;

import com.zoo.zoo.animal.AnimalMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnclosureMapper {

    private final SizeEnumConverter sizeEnumConverter;
    private final LocationEnumConverter locationEnumConverter;
    private final AnimalMapper animalMapper;

    public Enclosure fromRequest(EnclosureRequest enclosureRequest) {
        return Enclosure.builder()
                .name(enclosureRequest.name())
                .size(sizeEnumConverter.convert(enclosureRequest.size()))
                .location(locationEnumConverter.convert(enclosureRequest.location()))
                .objects(enclosureRequest.objects())
                .build();
    }

    public EnclosureResponse toResponse(Enclosure enclosure) {
        return EnclosureResponse.builder()
                .name(enclosure.getName())
                .size(enclosure.getSize())
                .location(enclosure.getLocation())
                .objects(enclosure.getObjects())
                .animals(enclosure.getAnimals().stream().map(animalMapper::toDTO).toList())
                .build();
    }

    public EnclosureDTO toDTO(Enclosure enclosure) {
        return EnclosureDTO.builder()
                .name(enclosure.getName())
                .size(enclosure.getSize())
                .location(enclosure.getLocation())
                .objects(enclosure.getObjects())
                .build();
    }

}
