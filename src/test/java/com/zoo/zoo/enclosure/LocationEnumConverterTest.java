package com.zoo.zoo.enclosure;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LocationEnumConverterTest {

    private LocationEnumConverter locationEnumConverter;

    @Before
    public void setup() {
        locationEnumConverter = new LocationEnumConverter();
    }

    @Test
    public void testConvert_ValidLocation() {
        String source = "INSIDE";
        Location expected = Location.INSIDE;
        Location result = locationEnumConverter.convert(source);

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testConvert_InvalidLocation() {
        String source = "UNKNOWN";
        Location result = locationEnumConverter.convert(source);

        Assert.assertNull(result);
    }

}
