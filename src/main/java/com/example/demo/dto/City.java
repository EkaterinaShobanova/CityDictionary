package com.example.demo.dto;

import java.util.*;

/**
 * The class represents the city in Russia
 * @param id - id of the city
 * @param name - name of the city
 * @param region - specific geographic area
 * @param district - specific administrative area
 * @param population - amount of citizens
 * @param foundation - the year of foundation
 */
public record City(UUID id, String name, String region, String district, Integer population, String foundation) {
    public City(String name) {
        this(UUID.randomUUID(), name, null, null, null, null);
    }
}
