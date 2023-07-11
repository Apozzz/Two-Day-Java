package com.zoo.zoo.enclosure;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SizeEnumConverterTest {

    private SizeEnumConverter sizeEnumConverter;

    @Before
    public void setup() {
        sizeEnumConverter = new SizeEnumConverter();
    }

    @Test
    public void testConvert_ValidSize() {
        String source = "SMALL";
        Size expected = Size.SMALL;
        Size result = sizeEnumConverter.convert(source);

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testConvert_InvalidSize() {
        String source = "UNKNOWN";
        Size result = sizeEnumConverter.convert(source);

        Assert.assertNull(result);
    }

}
