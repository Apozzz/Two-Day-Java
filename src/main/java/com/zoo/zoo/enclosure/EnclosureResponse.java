package com.zoo.zoo.enclosure;

import java.util.List;

import com.zoo.zoo.animal.AnimalDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnclosureResponse {

    private String name;
    private Size size;
    private Location location;
    private List<String> objects;
    private List<AnimalDTO> animals;

}