package com.zoo.zoo.enclosure;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.zoo.zoo.animal.AnimalMapper;

import java.util.Arrays;
import java.util.Collections;

public class EnclosureMapperTest {

    @Mock
    private SizeEnumConverter sizeEnumConverter;

    @Mock
    private LocationEnumConverter locationEnumConverter;

    @Mock
    private AnimalMapper animalMapper;

    private EnclosureMapper enclosureMapper;

    @Before
    public void setup() {
        sizeEnumConverter = Mockito.mock(SizeEnumConverter.class);
        locationEnumConverter = Mockito.mock(LocationEnumConverter.class);
        animalMapper = Mockito.mock(AnimalMapper.class);
        enclosureMapper = new EnclosureMapper(sizeEnumConverter, locationEnumConverter, animalMapper);
    }

    @Test
    public void testFromRequest() {
        EnclosureRequest request = new EnclosureRequest("Enclosure 1", "SMALL", "INSIDE",
                Arrays.asList("Tree", "Rock"));
        Mockito.when(sizeEnumConverter.convert("SMALL")).thenReturn(Size.SMALL);
        Mockito.when(locationEnumConverter.convert("INSIDE")).thenReturn(Location.INSIDE);
        Enclosure result = enclosureMapper.fromRequest(request);

        Assert.assertEquals("Enclosure 1", result.getName());
        Assert.assertEquals(Size.SMALL, result.getSize());
        Assert.assertEquals(Location.INSIDE, result.getLocation());
        Assert.assertEquals(Arrays.asList("Tree", "Rock"), result.getObjects());
    }

    @Test
    public void testToResponse() {
        Enclosure enclosure = Enclosure.builder()
                .name("Enclosure 1")
                .size(Size.MEDIUM)
                .location(Location.INSIDE)
                .objects(Arrays.asList("Tree", "Water Trough"))
                .animals(Collections.emptyList())
                .build();

        EnclosureResponse result = enclosureMapper.toResponse(enclosure);

        Assert.assertEquals("Enclosure 1", result.getName());
        Assert.assertEquals(Size.MEDIUM, result.getSize());
        Assert.assertEquals(Location.INSIDE, result.getLocation());
        Assert.assertEquals(Arrays.asList("Tree", "Water Trough"), result.getObjects());
        Assert.assertEquals(Collections.emptyList(), result.getAnimals());
    }

    @Test
    public void testToDTO() {
        Enclosure enclosure = new Enclosure(1L, "Enclosure 1", Size.SMALL, Location.INSIDE,
                Arrays.asList("Tree", "Rock"), null);
        EnclosureDTO result = enclosureMapper.toDTO(enclosure);

        Assert.assertEquals("Enclosure 1", result.name());
        Assert.assertEquals(Size.SMALL, result.size());
        Assert.assertEquals(Location.INSIDE, result.location());
        Assert.assertEquals(Arrays.asList("Tree", "Rock"), result.objects());
    }

}
