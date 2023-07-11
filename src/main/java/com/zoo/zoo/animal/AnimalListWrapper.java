package com.zoo.zoo.animal;

import java.util.List;

import lombok.Data;

@Data
public class AnimalListWrapper {

    private List<AnimalRequest> animals;

}
