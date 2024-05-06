package com.example.demo.rest;

import com.example.demo.dto.City;
import com.example.demo.service.CsvExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DataController {
    public Logger log = LoggerFactory.getLogger(DataController.class);

    @Autowired
    private CsvExtractor csvExtractor;

    @GetMapping(
            value = "/cities",
            params = { "district" }
    )
    public ResponseEntity<List<City>> citiesGroupedByDistrict(
            @RequestParam("district") List<String> district) {
        log.info("Called citiesGroupedByDistrict with district = " + district);
        return new ResponseEntity<>(csvExtractor.filterCitiesByDistrict(district), HttpStatus.OK);
    }

    @GetMapping("/cities")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseEntity<List<City>> cities() {
        log.info("Called full cities list");
        return new ResponseEntity<>(csvExtractor.sortCitiesByName(), HttpStatus.OK);
    }
}
