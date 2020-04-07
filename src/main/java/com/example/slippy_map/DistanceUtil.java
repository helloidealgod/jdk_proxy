package com.example.slippy_map;

public class DistanceUtil {
    private static double EARTH_RADIUS = 6371.393;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 计算两个经纬度之间的距离
     *
     * @param lng1 第一个地点的经度
     * @param lat1 第一个地点的纬度
     * @param lng2 第二个地点的经度
     * @param lat2 第二个地点的纬度
     * @return
     */
    public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.abs(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2))));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 1000);
        s = s / 1000;
        return s;
    }

    public static void main(String[] args) {
        double lon1 = 110.4628000000000d;
        double lat1 = 27.5656000000000d;
        double lon2 = 110.7612420000000d;
        double lat2 = 28.1668320000000d;
        System.out.println(DistanceUtil.getDistance(lon1,lat1,lon2,lat2));
    }
}
