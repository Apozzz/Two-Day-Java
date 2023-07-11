package com.zoo.zoo.enclosure;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Size {
    SMALL(1),
    MEDIUM(2),
    LARGE(4),
    HUGE(8);

    private final int value;
}
