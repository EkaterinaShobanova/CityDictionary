package com.example.demo.service;

import com.example.demo.dto.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

import static java.util.stream.Collectors.*;

@Service
public class CsvExtractorImpl implements CsvExtractor {
    private static final String FILE_NAME = "src/main/java/com/example/demo/output.csv";
    public static List<City> CACHED_CITIES;
    public Logger log = LoggerFactory.getLogger(CsvExtractorImpl.class);

    public List<City> sortCitiesByName() {
        List<City> cloned = new ArrayList<>(extractCitiesFromFile());
        cloned.sort(Comparator.comparing(City::name, String.CASE_INSENSITIVE_ORDER));
        return cloned;
    }

    public List<City> filterCitiesByDistrict(List<String> districts) {
        return extractCitiesFromFile()
                .stream()
                .filter(city -> districts.contains(city.district()))
                .collect(toList());
    }

    private List<City> extractCitiesFromFile() {
        try {
            if (CACHED_CITIES != null) {
                log.info("Cached city list was reused");
                return CACHED_CITIES;
            }

            log.info("Reading the cities file");
            Scanner scanner = new Scanner(new File(FILE_NAME));
            List<City> russianCities = new ArrayList<>();
            scanner.useDelimiter(";|\\R");
            while (scanner.hasNext()) {
                String idStr = scanner.next();

                if (isValidUUID(idStr)) {
                    UUID id = UUID.fromString(idStr);
                    String name = scanner.next();
                    String region = scanner.next();
                    String district = scanner.next();
                    int population = scanner.nextInt();
                    String foundation = scanner.next();
                    City newcity = new City(id, name, region, district, population, foundation);
                    russianCities.add(newcity);
                } else {
                    // Handle the case where the UUID is not valid (log an error, skip the entry, etc.)
                    log.error("Invalid UUID String: " + idStr);
                }
            }

            CACHED_CITIES = russianCities;
            return russianCities;
        } catch (Exception exception) {
            log.error("IO exception", exception);
            throw new RuntimeException(exception);
        }
    }

    private static boolean isValidUUID(String str) {
        try {
            String idStr = str.trim();  // Trim the whitespace from the input string
            UUID.fromString(idStr);  // Use the trimmed string to create the UUID
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
