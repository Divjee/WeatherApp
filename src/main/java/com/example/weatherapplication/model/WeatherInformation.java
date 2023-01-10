package com.example.weatherapplication.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class WeatherInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weather_information_seq")
    @SequenceGenerator(name = "weather_information_seq", sequenceName = "weather_information_seq", allocationSize = 1)
    private Long id;

    double temperature;

    double humidity;

    String city;

    LocalDateTime time;

    public WeatherInformation(double tempCelsius, float humidity, String city, LocalDateTime now) {
        this.temperature = tempCelsius;
        this.humidity = humidity;
        this.city = city;
        this.time = now;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WeatherInformation that = (WeatherInformation) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
