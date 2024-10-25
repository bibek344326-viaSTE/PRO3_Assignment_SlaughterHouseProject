package org.example.station1_animalregistration.server;

public class Station {
    private Long station_id;
    private String station_name;

    public Station(Long station_id, String station_name) {
        this.station_id = station_id;
        this.station_name = station_name;
    }

    public Long getId() {
        return station_id;
    }

    public String getStation_name() {
        return station_name;
    }
    @Override
    public String toString() {
        return "Station [ID=" + station_id + ", Name=" + station_name + "]";
    }
}
