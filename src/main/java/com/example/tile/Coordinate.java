package com.example.tile;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2019/12/2 14:49
 */
public class Coordinate {
    private double longitude;
    private double latitude;

    public Coordinate(){
    }

    public Coordinate(double longitude, double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
