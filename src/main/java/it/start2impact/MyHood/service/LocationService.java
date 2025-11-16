package it.start2impact.MyHood.service;

import it.start2impact.MyHood.entities.LocationEntity;
import it.start2impact.MyHood.repositories.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService  {
    private static final Logger logger = LoggerFactory.getLogger(LocationService.class);

    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }


    public List<String> findLocations(String name) {
        logger.info("LocationService.findLocations - {}", name);
        List<LocationEntity> locations = locationRepository.findByName(name);
        List<String> locationsName = new ArrayList<>();
        for(LocationEntity location : locations){
            locationsName.add(location.getLocation());
        }
        return locationsName;
    }
}
