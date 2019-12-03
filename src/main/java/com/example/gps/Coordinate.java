package com.example.gps;


import java.math.BigDecimal;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2019/12/2 14:49
 */
public class Coordinate {
    private BigDecimal longitude;
    private BigDecimal latitude;

    public Coordinate(){
    }

    public Coordinate(BigDecimal longitude,BigDecimal latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
}
