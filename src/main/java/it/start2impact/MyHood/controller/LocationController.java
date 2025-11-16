package it.start2impact.MyHood.controller;

import it.start2impact.MyHood.exceptions.MyHoodException;
import it.start2impact.MyHood.service.LocationService;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {
    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService){
        this.locationService = locationService;
    }

    @GetMapping("/find")
    public ResponseEntity<List<String>> locationName(@RequestParam(name = "name") String name) throws MyHoodException {
         logger.info("LocationController.locationName - {}", name);
         if(name.length() > 3){
             List<String> locations = locationService.findLocations(name);
             return ResponseEntity.status(HttpStatus.OK).body(locations);
         }
         throw new MyHoodException("Insert almost 3 character", HttpStatus.BAD_REQUEST);
    }

}
