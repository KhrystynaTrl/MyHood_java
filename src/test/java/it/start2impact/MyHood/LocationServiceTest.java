package it.start2impact.MyHood;

import it.start2impact.MyHood.entities.LocationEntity;
import it.start2impact.MyHood.repositories.LocationRepository;
import it.start2impact.MyHood.service.LocationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private  LocationService locationService;

    @Test
    void findLocations(){
        String locationName = "Roma";
        LocationEntity l1 = new LocationEntity();
        l1.setLocation("Roma Garbatella");

        LocationEntity l2 = new LocationEntity();
        l2.setLocation("Roma Ostiense");

        when(locationRepository.findByName(locationName)).thenReturn(List.of(l1, l2));

        List<String> result = locationService.findLocations(locationName);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Roma Garbatella", result.get(0));
        assertEquals("Roma Ostiense", result.get(1));
        verify(locationRepository).findByName(locationName);
    }

@Test
void findLocations_whenNoLocations_returnsEmptyList() {
    // ARRANGE
    String name = "CittaInesistente";

    when(locationRepository.findByName(name))
            .thenReturn(List.of());

    // ACT
    List<String> result = locationService.findLocations(name);

    // ASSERT
    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(locationRepository).findByName(name);
}
}