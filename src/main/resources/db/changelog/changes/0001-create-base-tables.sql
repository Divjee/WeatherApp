--liquibase formatted sql
--changeset liquibase-docs:1
CREATE TABLE weather_information (
                                     id BIGINT PRIMARY KEY NOT NULL,
                                     temperature DECIMAL(5, 2) NOT NULL,
                                     humidity DECIMAL(5, 2) NOT NULL,
                                     city VARCHAR(255) NULL,
                                     time TIMESTAMP NULL
);