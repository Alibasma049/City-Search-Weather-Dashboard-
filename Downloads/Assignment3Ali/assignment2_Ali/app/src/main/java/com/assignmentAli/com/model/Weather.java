package com.assignmentAli.com.model;

public class Weather {

    private final String city;
    private final String country;
    private final double tempC;
    private final double tempF;
    private final double feelsLike;
    private final int humidity;
    private final double windSpeed;
    private final String windDirection;
    private final String icon;
    private final String conditionText;

    public Weather(String city,
                   String country,
                   double tempC,
                   double tempF,
                   double feelsLike,
                   int humidity,
                   double windSpeed,
                   String windDirection,
                   String icon,
                   String conditionText) {

        this.city = city;
        this.country = country;
        this.tempC = tempC;
        this.tempF = tempF;
        this.feelsLike = feelsLike;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.icon = icon;
        this.conditionText = conditionText;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public double getTempC() {
        return tempC;
    }

    public double getTempF() {
        return tempF;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public String getIcon() {
        return icon;
    }

    public String getConditionText() {
        return conditionText;
    }
}
