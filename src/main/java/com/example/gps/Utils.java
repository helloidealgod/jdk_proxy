//package com.example.gps;
//
//
//import com.bi.bigmath.BigMath;
//import com.bi.bigmath.PrecisionHolder;
//
//import java.math.BigDecimal;
//
///**
// * @Description:
// * @Author: xiankun.jiang
// * @Date: 2019/12/3 08:41
// */
//public class Utils {
//    private static final int SCALE = 13;
//    private static final BigDecimal PI = new BigDecimal("3.14159265358979324");
//    private static final BigDecimal b3000 = new BigDecimal("3000.0");
//    private static final BigDecimal b180 = new BigDecimal("180.0");
//    private static final BigDecimal a = new BigDecimal("6378245.0");
//    private static final BigDecimal ee = new BigDecimal("0.00669342162296594323");
//    private static final BigDecimal XPI = PI.multiply(b3000).divide(b180, SCALE, BigDecimal.ROUND_HALF_UP);
//    private static PrecisionHolder precision = new PrecisionHolder() {
//        @Override
//        public int getPrecision() {
//            return SCALE;
//        }
//    };
//    private static BigMath bigMath = BigMath.getDefaultBigMath(precision);
//    /**
//     * @description: 地球坐标系(WGS - 84)转火星坐标系(GCJ)
//     * @param:
//     * @return:
//     * @author: xiankun.jiang
//     * @date: 2019/12/2 15:17
//     */
////    public static Coordinate wgs84ToMars(Coordinate wgs84Coordinate) {
////        Coordinate marsCoordinate = new Coordinate();
////        BigDecimal wgLon = wgs84Coordinate.getLongitude();
////        BigDecimal wgLat = wgs84Coordinate.getLatitude();
////        Coordinate d = delta(wgs84Coordinate);
////        marsCoordinate.setLatitude(wgLat.add(d.getLatitude()));
////        marsCoordinate.setLongitude(wgLon.add(d.getLongitude()));
////        return marsCoordinate;
////    }
////
////    /**
////     * @description: 火星坐标系(GCJ)转地球坐标系(WGS - 84)
////     * @param:
////     * @return:
////     * @author: xiankun.jiang
////     * @date: 2019/12/2 15:35
////     */
////    public static Coordinate marsToWGS84(Coordinate marsCoordinate) {
////        Coordinate d = delta(marsCoordinate);
////        Coordinate wgs84Coordinate = new Coordinate();
////        wgs84Coordinate.setLatitude(marsCoordinate.getLatitude().subtract(d.getLatitude()));
////        wgs84Coordinate.setLongitude(marsCoordinate.getLongitude().subtract(d.getLongitude()));
////        return wgs84Coordinate;
////    }
////
////    private static Coordinate delta(Coordinate marsCoordinate) {
////        BigDecimal b180 = new BigDecimal("180.0");
////        BigDecimal b1 = new BigDecimal("1");
////        BigDecimal b105 = new BigDecimal("105.0");
////        BigDecimal b35 = new BigDecimal("35.0");
////
////        BigDecimal dLat = transformLat(marsCoordinate.getLongitude().subtract(b105), marsCoordinate.getLatitude().subtract(b35));
////        BigDecimal dLon = transformLon(marsCoordinate.getLongitude().subtract(b105), marsCoordinate.getLatitude().subtract(b35));
////        BigDecimal radLat = marsCoordinate.getLatitude().multiply(PI).divide(b180,SCALE,BigDecimal.ROUND_HALF_UP);
////        BigDecimal magic = bigMath.sin(radLat);
////        magic = ee.multiply(magic).multiply(magic).subtract(b1);
////        BigDecimal sqrtMagic = bigMath.sqrt(magic);
////        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * PI);
////        dLon = (dLon * 180.0) / (a / sqrtMagic * bigMath.cos(radLat) * PI);
////        return new Coordinate(dLon, dLat);
////    }
////
////    private static BigDecimal transformLat(BigDecimal x, BigDecimal y) {
////        BigDecimal ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
////        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
////        ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
////        ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
////        return ret;
////    }
////
////    private static BigDecimal transformLon(BigDecimal x, BigDecimal y) {
////        BigDecimal ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
////        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
////        ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
////        ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;
////        return ret;
////    }
//
//    /**
//     * @description: 火星坐标转百度坐标
//     * @param:
//     * @return:
//     * @author: xiankun.jiang
//     * @date: 2019/12/2 15:05
//     */
//    public static Coordinate marsTobaidu(Coordinate marsCoordinate) {
//        Coordinate baiduCoordinate = new Coordinate();
//        BigDecimal x = marsCoordinate.getLongitude();
//        BigDecimal y = marsCoordinate.getLatitude();
//        BigDecimal z = bigMath.sqrt(x.multiply(x).add(y.multiply(y))).add(bigMath.sin(y.multiply(XPI)).multiply(new BigDecimal("0.00002")));
//        BigDecimal theta = bigMath.cos(x.multiply(XPI)).multiply(new BigDecimal("0.000003")).add(new BigDecimal(((Double) (Math.atan2(y.doubleValue(), x.doubleValue()))).toString()));
//        baiduCoordinate.setLongitude(bigMath.cos(theta).multiply(z).add(new BigDecimal("0.0065")));
//        baiduCoordinate.setLatitude(bigMath.sin(theta).multiply(z).add(new BigDecimal("0.006")));
//        return baiduCoordinate;
//    }
//
//    public static void main(String[] argv) {
//        //        谷歌地图：27.9470444288,110.7778974511
////        百度地图：27.953272056,110.7843283057
////        腾讯高德：27.94703494,110.7778992
////        谷歌地球：27.95039,110.77293
//        Coordinate marsCoordinate = new Coordinate(new BigDecimal("110.60031"), new BigDecimal("27.905265"));
//        Coordinate baiduCoordinate = marsTobaidu(marsCoordinate);
//        System.out.print("");
//    }
//}
