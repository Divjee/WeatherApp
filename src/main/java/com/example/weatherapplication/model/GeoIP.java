package com.example.weatherapplication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class GeoIP {
    private String ipAddress;
    private String city;
    private Double latitude;
    private Double longitude;
}
