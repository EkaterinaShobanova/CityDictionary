package com.example.demo.service;

import com.example.demo.dto.City;

import java.util.List;

public interface CsvExtractor {
    List<City> sortCitiesByName();
    List<City> filterCitiesByDistrict(List<String> districts);
}
