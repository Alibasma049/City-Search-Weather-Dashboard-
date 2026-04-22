package com.assignmentAli.com.model;

public class City {

    private final String id;
    private final String name;
    private final String region;
    private final String country;

    public City(String id, String name, String region, String country) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }
}
