package com.example.slippy_map;


/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/4/7 15:41
 */
public class Change {
    int zoom = 16;
    double l = 20037508.3427890167;
    //应在经纬度
    double realLongitude;
    double reallatitude;
    //实在经纬度
    double actuallongitude;
    double actuallatitude;

    double getRx() {
        // l / (256 * pow(2,zoom-1)
        return l / Math.pow(2, zoom + 6);
    }

    double getDeltaPixX() {
        return 0;
    }

    double getDeltaPixY() {
        return 0;
    }
}
