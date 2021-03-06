package com.example.tile;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2019/12/3 14:10
 */
public class Gps {
    private double wgLat;
    private double wgLon;

    public Gps() {

    }

    public Gps(double wgLat, double wgLon) {
        setWgLat(wgLat);
        setWgLon(wgLon);
    }

    public double getWgLat() {
        return wgLat;
    }

    public void setWgLat(double wgLat) {
        this.wgLat = wgLat;
    }

    public double getWgLon() {
        return wgLon;
    }

    public void setWgLon(double wgLon) {
        this.wgLon = wgLon;
    }

    @Override
    public String toString() {
        return wgLon + "," + wgLat;
    }
}