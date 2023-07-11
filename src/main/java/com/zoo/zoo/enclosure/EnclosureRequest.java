package com.zoo.zoo.enclosure;

import java.util.List;

import lombok.Builder;

@Builder
public record EnclosureRequest(
        String name,
        String size,
        String location,
        List<String> objects) {
}
