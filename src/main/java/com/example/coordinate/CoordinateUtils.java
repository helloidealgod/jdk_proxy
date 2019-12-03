package com.example.coordinate;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2019/12/2 14:44
 */
public class CoordinateUtils {
    private final double XPI = 3.14159265358979324 * 3000.0 / 180.0;
    private final double PI = 3.14159265358979324;
    private final double a = 6378245.0; //  a: 卫星椭球坐标投影到平面地图坐标系的投影因子。
    private final double ee = 0.00669342162296594323; //ee: 椭球的偏心率。

    /**
     * @description: 百度坐标转火星坐标
     * @param:
     * @return:
     * @author: xiankun.jiang
     * @date: 2019/12/2 15:05
     */
    public Coordinate baiduTomars(Coordinate baiduCoordinate) {
        Coordinate marsCoordinate = new Coordinate();
        double x = baiduCoordinate.getLongitude() - 0.0065;
        double y = baiduCoordinate.getLatitude() - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * XPI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * XPI);
        marsCoordinate.setLongitude(z * Math.cos(theta));
        marsCoordinate.setLatitude(z * Math.sin(theta));
        return marsCoordinate;
    }

    /**
     * @description: 火星坐标转百度坐标
     * @param:
     * @return:
     * @author: xiankun.jiang
     * @date: 2019/12/2 15:05
     */
    public Coordinate marsTobaidu(Coordinate marsCoordinate) {
        Coordinate baiduCoordinate = new Coordinate();
        double x = marsCoordinate.getLongitude();
        double y = marsCoordinate.getLatitude();
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * XPI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * XPI);
        baiduCoordinate.setLongitude(z * Math.cos(theta) + 0.0065);
        baiduCoordinate.setLatitude(z * Math.sin(theta) + 0.006);
        return baiduCoordinate;
    }


    /**
     * @description: 地球坐标系(WGS - 84)转火星坐标系(GCJ)
     * @param:
     * @return:
     * @author: xiankun.jiang
     * @date: 2019/12/2 15:17
     */
    public Coordinate wgs84ToMars(Coordinate wgs84Coordinate) {
        Coordinate marsCoordinate = new Coordinate();
        double wgLon = wgs84Coordinate.getLongitude();
        double wgLat = wgs84Coordinate.getLatitude();
        if (outOfChina(wgLat, wgLon)) {
            return wgs84Coordinate;
        }

//        double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
//        double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
//        double radLat = wgLat / 180.0 * PI;
//        double magic = Math.sin(radLat);
//        magic = 1 - ee * magic * magic;
//        double sqrtMagic = Math.sqrt(magic);
//        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * PI);
//        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * PI);
        Coordinate d = delta(wgs84Coordinate);
        marsCoordinate.setLatitude(wgLat + d.getLatitude());
        marsCoordinate.setLongitude(wgLon + d.getLongitude());
        return marsCoordinate;
    }

    /**
     * @description: 火星坐标系(GCJ)转地球坐标系(WGS - 84)
     * @param:
     * @return:
     * @author: xiankun.jiang
     * @date: 2019/12/2 15:35
     */
    public Coordinate marsToWGS84(Coordinate marsCoordinate) {
        Coordinate d = delta(marsCoordinate);
        Coordinate wgs84Coordinate = new Coordinate();
        wgs84Coordinate.setLatitude(marsCoordinate.getLatitude() - d.getLatitude());
        wgs84Coordinate.setLongitude(marsCoordinate.getLongitude() - d.getLongitude());
        return wgs84Coordinate;
    }

    private Coordinate delta(Coordinate marsCoordinate) {
        double dLat = transformLat(marsCoordinate.getLongitude() - 105.0, marsCoordinate.getLatitude() - 35.0);
        double dLon = transformLon(marsCoordinate.getLongitude() - 105.0, marsCoordinate.getLatitude() - 35.0);
        double radLat = marsCoordinate.getLatitude() / 180.0 * PI;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * PI);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * PI);
        return new Coordinate(dLon, dLat);
    }

    /*判断是否在国内，不在国内则不做偏移*/
    private boolean outOfChina(double longitude, double latitude) {
        return ((longitude < 72.004 || longitude > 137.8347) && (latitude < 0.8293 || latitude > 55.8271));
    }

    private double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }

    public static void main(String[] argv) {
        Coordinate coordinate = new Coordinate(	109.79706568444735,27.51359010961966);
        CoordinateUtils utils = new CoordinateUtils();
        Coordinate gaode =  utils.wgs84ToMars(coordinate);
    }
}
