package com.zoo.zoo.animal;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FoodTypeEnumConverter implements Converter<String, FoodType> {

    @Override
    public FoodType convert(String source) {
        try {
            return FoodType.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
