package com.example.demo;

import com.example.demo.dto.City;
import com.example.demo.rest.DataController;
import com.example.demo.service.CsvExtractor;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(DataController.class)
class CityDictionaryApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CsvExtractor csvExtractor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCitiesGroupedByDistrict() throws Exception {
        List<City> cities = List.of(new City("City1"), new City("City2"));

        List<String> districts = List.of("District1", "District2");
        when(csvExtractor.filterCitiesByDistrict(districts)).thenReturn(cities);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/cities")
                        .param("district", "District1", "District2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("City1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("City2"));
    }

    @Test
    public void testCities() throws Exception {

        List<City> cities = List.of(new City("City1"), new City("City2"));
        when(csvExtractor.sortCitiesByName()).thenReturn(cities);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/cities")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("City1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("City2"));
    }
}