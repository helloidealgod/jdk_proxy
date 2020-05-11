package com.example.tile;

import org.springframework.stereotype.Service;

/**
 * @Description: (GPS84 / CGCS200) 谷歌、天地图
 * (gcj02) 高德、腾讯
 * (bd09) 百度
 * @Author: xiankun.jiang
 * @Date: 2019/12/3 14:10
 */
@Service
public class GpsUtils {
    private static double pi = 3.1415926535897932384626;
    private static double a = 6378245.0;
    private static double ee = 0.00669342162296594323;

    public static Gps gps84ToGcj02(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return new Gps(lat, lon);
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return new Gps(mgLat, mgLon);
    }

    public static Gps gcjToGps84(double lat, double lon) {
        Gps gps = transform(lat, lon);
        double longitude = lon * 2 - gps.getWgLon();
        double latitude = lat * 2 - gps.getWgLat();
        return new Gps(latitude, longitude);
    }

    public static Gps gcj02ToBd09(double ggLat, double ggLon) {
        double x = ggLon, y = ggLat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * pi);
        double bdLon = z * Math.cos(theta) + 0.0065;
        double bdLat = z * Math.sin(theta) + 0.006;
        return new Gps(bdLat, bdLon);
    }

    public static Gps bd09ToGcj02(double bdLat, double bdLon) {
        double x = bdLon - 0.0065, y = bdLat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * pi);
        double ggLon = z * Math.cos(theta);
        double ggLat = z * Math.sin(theta);
        return new Gps(ggLat, ggLon);
    }

    public static Gps bd09ToGps84(double bdLat, double bdLon) {
        Gps gcj02 = GpsUtils.bd09ToGcj02(bdLat, bdLon);
        Gps map84 = GpsUtils.gcjToGps84(gcj02.getWgLat(), gcj02.getWgLon());
        return map84;
    }

    private static boolean outOfChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }

    private static Gps transform(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return new Gps(lat, lon);
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return new Gps(mgLat, mgLon);
    }

    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }

    public static void main(String[] args) {
        // 北斗芯片获取的经纬度为WGS84地理坐标 31.426896,119.496145
        Gps gps = new Gps(31.426896, 119.496145);
        System.out.println("原始gps :" + gps);
        Gps gcj = gps84ToGcj02(gps.getWgLat(), gps.getWgLon());
        System.out.println("gps84 -> gcj :" + gcj);
        Gps star = gcjToGps84(gcj.getWgLat(), gcj.getWgLon());
        System.out.println("gcj -> gps84 :" + star);
        Gps bd = gcj02ToBd09(gcj.getWgLat(), gcj.getWgLon());
        System.out.println("gcj -> bd  :" + bd);
        Gps gcj2 = bd09ToGcj02(bd.getWgLat(), bd.getWgLon());
        System.out.println("bd09 -> gcj :" + gcj2);
    }
}