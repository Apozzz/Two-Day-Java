package com.zoo.zoo.enclosure;

public class EnclosureNotFoundException extends RuntimeException {

    public EnclosureNotFoundException(String message) {
        super(message);
    }

    public EnclosureNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
