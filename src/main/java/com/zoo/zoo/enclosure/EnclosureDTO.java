package com.zoo.zoo.enclosure;

import java.util.List;

import lombok.Builder;

@Builder
public record EnclosureDTO(
        String name,
        Size size,
        Location location,
        List<String> objects) {
}
