package com.Assignment.Controller.Weather;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Assignment.Entity.WeatherInfo;
import com.Assignment.Service.WeatherService;

@RestController
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather")
    public ResponseEntity<WeatherInfo> getWeather(@RequestParam String pincode, @RequestParam String forDate) {
        LocalDate date = LocalDate.parse(forDate);
        WeatherInfo weatherInfo = weatherService.getWeatherForPincode(pincode, date);
        return ResponseEntity.ok(weatherInfo);
    }
}

