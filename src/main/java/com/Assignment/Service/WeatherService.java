package com.Assignment.Service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.Assignment.Entity.WeatherInfo;
import com.Assignment.Repository.WeatherInfoRepository;
import com.Assignment.Response.GeoResponse;
import com.Assignment.Response.WeatherResponse;

@Service
	public class WeatherService {

	    @Autowired
	    private WeatherInfoRepository weatherInfoRepository;

	    public WeatherInfo getWeatherForPincode(String pincode, LocalDate forDate) {
	        Optional<WeatherInfo> existingWeather = weatherInfoRepository.findByPincodeAndForDate(pincode, forDate);
	        
	        if (existingWeather.isPresent()) {
	            return existingWeather.get();  // Use cached data
	        } else {
	            // Call OpenWeather API and save data
	            WeatherInfo weatherInfo = fetchWeatherFromAPI(pincode, forDate);
	            return weatherInfoRepository.save(weatherInfo);
	        }
	    }

	 
	    private WeatherInfo fetchWeatherFromAPI(String pincode, LocalDate forDate) {
	        // Call OpenWeather Geocoding API to get latitude and longitude
	        String geocodeUrl = "https://api.openweathermap.org/geo/1.0/zip?zip=" + pincode + "&appid=1beb4a3a7b310c730634e1136cdb2fbd";

	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<GeoResponse> response = restTemplate.getForEntity(geocodeUrl, GeoResponse.class);
	        
	        // Check for a successful response
	        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
	            // Extract lat and lon from response
	            Double lat = response.getBody().getLat();
	            Double lon = response.getBody().getLon();
	            
	            // Call OpenWeather API for weather data using latitude and longitude
	            String weatherUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=1beb4a3a7b310c730634e1136cdb2fbd";
	            ResponseEntity<WeatherResponse> weatherResponse = restTemplate.getForEntity(weatherUrl, WeatherResponse.class);
	            
	            // Check for a successful weather response
	            if (weatherResponse.getStatusCode().is2xxSuccessful() && weatherResponse.getBody() != null) {
	                // Map weatherResponse to WeatherInfo object
	                WeatherInfo weatherInfo = new WeatherInfo();
	                weatherInfo.setPincode(pincode);
	                weatherInfo.setLatitude(lat);
	                weatherInfo.setLongitude(lon);
	                weatherInfo.setWeatherDescription(weatherResponse.getBody().getWeather().get(0).getDescription());
	                weatherInfo.setTemperature(weatherResponse.getBody().getMain().getTemp());
	                weatherInfo.setHumidity(weatherResponse.getBody().getMain().getHumidity());
	                weatherInfo.setForDate(forDate);
	                
	                return weatherInfo;
	            } else {
	                throw new RuntimeException("Failed to fetch weather data: " + weatherResponse.getBody());
	            }
	        } else {
	            throw new RuntimeException("Failed to fetch geocode data: " + response.getBody());
	        }
	    }



}

