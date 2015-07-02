package io.wake.wear.common.model;

import io.realm.RealmObject;

public class Preset extends RealmObject {
    private String name;
    private double latitude;
    private double longitude;
    private String mac;

    private long id;

    public Preset() {
    }

    public Preset(String name, double latitude, double longitude, String mac) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;

        this.mac = mac;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
