package com.zoo.zoo.animal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnimalResponse {

    private String species;
    private FoodType food;
    private Integer amount;

}
