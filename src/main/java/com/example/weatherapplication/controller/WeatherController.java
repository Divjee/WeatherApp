package com.example.weatherapplication.controller;

import com.example.weatherapplication.model.WeatherInformation;
import com.example.weatherapplication.service.WeatherService;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("app")
public class WeatherController {
    @Autowired
    WeatherService weatherService;

    @GetMapping("/weather")
    public WeatherInformation insertData() throws IOException, GeoIp2Exception {
        return weatherService.getWeatherFromLocation(weatherService.getLocation().getLatitude(),weatherService.getLocation().getLongitude());
    }

    @GetMapping("/weathers/{latitude}/{longitude}")
    public WeatherInformation insertData1(@PathVariable double latitude,@PathVariable double longitude ) throws IOException, GeoIp2Exception {
        return weatherService.getWeatherFromLocation(latitude,longitude);
    }

    @RequestMapping ("/nothing")
    public void clearData(){
        weatherService.clearAllData();
    }
}
