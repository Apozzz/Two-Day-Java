package com.zoo.zoo.animal;

import org.junit.Assert;
import org.junit.Test;

public class FoodTypeEnumConverterTest {

    private FoodTypeEnumConverter foodTypeEnumConverter = new FoodTypeEnumConverter();

    @Test
    public void testConvert_ValidFoodType() {
        String source = "CARNIVORE";
        FoodType result = foodTypeEnumConverter.convert(source);

        Assert.assertEquals(FoodType.CARNIVORE, result);
    }

    @Test
    public void testConvert_InvalidFoodType() {
        String source = "UNKNOWN";
        FoodType result = foodTypeEnumConverter.convert(source);

        Assert.assertNull(result);
    }

    @Test(expected = NullPointerException.class)
    public void testConvert_NullSource() {
        String source = null;
        foodTypeEnumConverter.convert(source);
    }

}
