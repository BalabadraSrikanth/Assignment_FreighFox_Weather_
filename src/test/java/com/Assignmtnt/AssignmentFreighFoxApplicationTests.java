package com.Assignmtnt;

import com.Assignment.Entity.WeatherInfo;
import com.Assignment.Repository.WeatherInfoRepository;
import com.Assignment.Response.GeoResponse;
import com.Assignment.Response.WeatherResponse;
import com.Assignment.Service.WeatherService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class AssignmentFreighFoxApplicationTests {

	

	class WeatherServiceTest {

	    @Mock
	    private WeatherInfoRepository weatherInfoRepository;

	    @Mock
	    private RestTemplate restTemplate;

	    @InjectMocks
	    private WeatherService weatherService;

	    @Test
	    void testGetWeatherForPincode_WhenCachedDataExists() {
	        // Arrange
	        String pincode = "500001";
	        LocalDate forDate = LocalDate.now();
	        WeatherInfo cachedWeatherInfo = new WeatherInfo();
	        cachedWeatherInfo.setPincode(pincode);
	        cachedWeatherInfo.setTemperature(300.0);
	        
	        when(weatherInfoRepository.findByPincodeAndForDate(pincode, forDate))
	                .thenReturn(Optional.of(cachedWeatherInfo));

	        // Act
	        WeatherInfo result = weatherService.getWeatherForPincode(pincode, forDate);

	        // Assert
	        assertNotNull(result);
	        assertEquals(300.0, result.getTemperature());
	        Mockito.verify(restTemplate, Mockito.never()).getForEntity(anyString(), eq(GeoResponse.class));
	    }

	    @Test
	    void testGetWeatherForPincode_WhenCachedDataDoesNotExist() {
	        // Arrange
	        String pincode = "500001";
	        LocalDate forDate = LocalDate.now();

	        GeoResponse mockGeoResponse = new GeoResponse();
	        mockGeoResponse.setLat(17.3850);
	        mockGeoResponse.setLon(78.4867);

	        WeatherResponse.Main mockMain = new WeatherResponse.Main();
	        mockMain.setTemp(303.15);
	        mockMain.setHumidity(40.0);

	        WeatherResponse.Weather mockWeather = new WeatherResponse.Weather();
	        mockWeather.setDescription("clear sky");

	        WeatherResponse mockWeatherResponse = new WeatherResponse();
	        mockWeatherResponse.setMain(mockMain);
	        mockWeatherResponse.setWeather(List.of(mockWeather));

	        when(weatherInfoRepository.findByPincodeAndForDate(pincode, forDate)).thenReturn(Optional.empty());
	        when(restTemplate.getForEntity(anyString(), eq(GeoResponse.class)))
	                .thenReturn(new ResponseEntity<>(mockGeoResponse, HttpStatus.OK));
	        when(restTemplate.getForEntity(anyString(), eq(WeatherResponse.class)))
	                .thenReturn(new ResponseEntity<>(mockWeatherResponse, HttpStatus.OK));

	        // Act
	        WeatherInfo result = weatherService.getWeatherForPincode(pincode, forDate);

	        // Assert
	        assertNotNull(result);
	        assertEquals(17.3850, result.getLatitude());
	        assertEquals(303.15, result.getTemperature());
	        assertEquals("clear sky", result.getWeatherDescription());
	    }

	    @Test
	    void testGetWeatherForPincode_WhenGeocodingFails() {
	        // Arrange
	        String pincode = "invalid";
	        LocalDate forDate = LocalDate.now();

	        when(restTemplate.getForEntity(anyString(), eq(GeoResponse.class)))
	                .thenThrow(new RuntimeException("Failed to fetch geocode data"));

	        // Act & Assert
	        Exception exception = assertThrows(RuntimeException.class, () -> {
	            weatherService.getWeatherForPincode(pincode, forDate);
	        });

	        assertEquals("Failed to fetch geocode data", exception.getMessage());
	    }
}}