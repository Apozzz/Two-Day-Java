package com.zoo.zoo.enclosure;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class EnclosureControllerTest {

    @Mock
    private EnclosureService enclosureService;

    private EnclosureController enclosureController;

    @Before
    public void setup() {
        enclosureService = Mockito.mock(EnclosureService.class);
        enclosureController = new EnclosureController(enclosureService);
    }

    @Test
    public void testGetEnclosures() {
        List<EnclosureResponse> enclosureResponses = Arrays.asList(
                new EnclosureResponse("Enclosure 1", Size.SMALL, Location.INSIDE, Arrays.asList("Tree", "Rock"), null),
                new EnclosureResponse("Enclosure 2", Size.MEDIUM, Location.OUTSIDE, Arrays.asList("Bush", "Pond"),
                        null));
        Mockito.when(enclosureService.getEnclosuresResponse()).thenReturn(enclosureResponses);
        List<EnclosureResponse> result = enclosureController.getEnclosures();

        Assert.assertEquals(enclosureResponses, result);
        Mockito.verify(enclosureService, Mockito.times(1)).getEnclosuresResponse();
    }

    @Test
    public void testSaveEnclosures_Success() {
        EnclosureListWrapper enclosureListWrapper = new EnclosureListWrapper();
        String result = enclosureController.saveEnclosures(enclosureListWrapper);

        Assert.assertEquals("Enclosures were saved successfully!", result);
        Mockito.verify(enclosureService, Mockito.times(1)).saveEnclosures(enclosureListWrapper);
    }

    @Test
    public void testSaveEnclosures_Exception() {
        EnclosureListWrapper enclosureListWrapper = new EnclosureListWrapper();
        Mockito.doThrow(new RuntimeException()).when(enclosureService).saveEnclosures(enclosureListWrapper);
        String result = enclosureController.saveEnclosures(enclosureListWrapper);

        Assert.assertEquals("Enclosures were not saved", result);
        Mockito.verify(enclosureService, Mockito.times(1)).saveEnclosures(enclosureListWrapper);
    }

    @Test
    public void testAddAnimalToEnclosure() {
        Long enclosureId = 1L;
        Long animalId = 1L;
        String result = enclosureController.addAnimalToEnclosure(enclosureId, animalId);

        Assert.assertEquals("Animal added to enclosure successfully", result);
        Mockito.verify(enclosureService, Mockito.times(1)).addAnimalToEnclosure(enclosureId, animalId);
    }

    @Test
    public void testDeleteEnclosures() {
        String result = enclosureController.deleteEnclosures();

        Assert.assertEquals("Enclosures deleted successfully", result);
        Mockito.verify(enclosureService, Mockito.times(1)).deleteEnclosures();
    }

    @Test
    public void testRemoveAnimalFromEnclosure() {
        Long enclosureId = 1L;
        Long animalId = 1L;
        String result = enclosureController.removeAnimalFromEnclosure(enclosureId, animalId);

        Assert.assertEquals("Animal removed from enclosure successfully", result);
        Mockito.verify(enclosureService, Mockito.times(1)).removeAnimalFromEnclosure(enclosureId, animalId);
    }

}
