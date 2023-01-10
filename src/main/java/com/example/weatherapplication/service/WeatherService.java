package com.example.weatherapplication.service;

import com.example.weatherapplication.model.GeoIP;
import com.example.weatherapplication.model.WeatherInformation;
import com.example.weatherapplication.repository.WeatherRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spark.utils.IOUtils;

import javax.sql.DataSource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

@Service
public class WeatherService {
    @Autowired
    WeatherRepository repository;



    public GeoIP getLocation()
            throws IOException, GeoIp2Exception {
        URL connection1 = new URL("http://checkip.amazonaws.com/");
        URLConnection con = connection1.openConnection();
        String ip = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        ip = reader.readLine();
        File database = new File("src/main/resources/GeoLite2-City.mmdb");
        DatabaseReader dbReader = new DatabaseReader.Builder(database).build();
        InetAddress ipAddress = InetAddress.getByName(ip);
        CityResponse response = dbReader.city(ipAddress);

        String cityName = response.getCity().getName();
        double latitude =
                response.getLocation().getLatitude();
        double longitude =
                response.getLocation().getLongitude();
        return new GeoIP(ip, cityName, latitude, longitude);
    }

    public WeatherInformation getWeatherFromLocation(double latitude, double longitude) throws IOException, GeoIp2Exception {

        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=1fbfe510293b4659d274557de7c33380";

        URL weatherUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) weatherUrl.openConnection();
        connection.setRequestMethod("GET");

        InputStream inputStream = connection.getInputStream();
        String response = IOUtils.toString(inputStream);

        Gson gson = new Gson();
        JsonObject json = gson.fromJson(response, JsonObject.class);
        JsonObject currentObservation = json.getAsJsonObject("main");

        float temperature = currentObservation.get("temp").getAsFloat();
        float humidity = currentObservation.get("humidity").getAsFloat();
        String cityName = json.get("name").getAsString();
        double temp = temperature - 273.15;
        double tempCelsius= (Math.round(temp * 100))/100.0;
        repository.save(new WeatherInformation(tempCelsius,humidity,cityName, LocalDateTime.now()));

        return   new WeatherInformation(tempCelsius,humidity,cityName, LocalDateTime.now());
    }

    public void clearAllData(){
        repository.deleteAll();
    }

}
