package com.zoo.zoo.animal;

public record AnimalRequest(
        String species,
        String food,
        Integer amount) {
}