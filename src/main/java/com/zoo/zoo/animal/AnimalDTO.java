package com.zoo.zoo.animal;

import lombok.Builder;

@Builder
public record AnimalDTO(
        String species,
        FoodType food,
        Integer amount) {
}
