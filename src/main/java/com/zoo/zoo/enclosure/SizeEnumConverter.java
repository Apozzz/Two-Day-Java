package com.zoo.zoo.enclosure;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SizeEnumConverter implements Converter<String, Size> {

    @Override
    public Size convert(String source) {
        try {
            return Size.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
