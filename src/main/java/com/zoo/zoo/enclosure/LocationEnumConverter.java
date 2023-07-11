package com.zoo.zoo.enclosure;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LocationEnumConverter implements Converter<String, Location> {

    @Override
    public Location convert(String source) {
        try {
            return Location.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
