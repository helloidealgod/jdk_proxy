//package com.example.gps;
//
//import com.bi.bigmath.BigMath;
//import com.bi.bigmath.PrecisionHolder;
//
//import java.math.BigDecimal;
//
///**
// * @Description:
// * @Author: xiankun.jiang
// * @Date: 2019/12/2 14:44
// */
//public class CoordinateUtils {
//    private final BigDecimal XPI = new BigDecimal(((Double) (3.14159265358979324 * 3000.0 / 180.0)).toString());
//    private final BigDecimal PI = new BigDecimal("3.14159265358979324");
//    private final BigDecimal a = new BigDecimal("6378245.0");
//    private final BigDecimal ee = new BigDecimal("0.00669342162296594323");
//    private static PrecisionHolder precision = new PrecisionHolder() {
//        @Override
//        public int getPrecision() {
//            return 16;
//        }
//    };
//    private static BigMath bigMath = BigMath.getDefaultBigMath(precision);
//    /**
//     * @description: 百度坐标转火星坐标
//     * @param:
//     * @return:
//     * @author: xiankun.jiang
//     * @date: 2019/12/2 15:05
//     */
////    public Coordinate baiduTomars(Coordinate baiduCoordinate) {
////        Coordinate marsCoordinate = new Coordinate();
////        double x = baiduCoordinate.getLongitude() - 0.0065;
////        double y = baiduCoordinate.getLatitude() - 0.006;
////        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * XPI);
////        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * XPI);
////        marsCoordinate.setLongitude(z * Math.cos(theta));
////        marsCoordinate.setLatitude(z * Math.sin(theta));
////        return marsCoordinate;
////    }
//
//    /**
//     * @description: 火星坐标转百度坐标
//     * @param:
//     * @return:
//     * @author: xiankun.jiang
//     * @date: 2019/12/2 15:05
//     */
////    public Coordinate marsTobaidu(Coordinate marsCoordinate) {
////        Coordinate baiduCoordinate = new Coordinate();
////        double x = marsCoordinate.getLongitude();
////        double y = marsCoordinate.getLatitude();
////        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * XPI);
////        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * XPI);
////        baiduCoordinate.setLongitude(z * Math.cos(theta) + 0.0065);
////        baiduCoordinate.setLatitude(z * Math.sin(theta) + 0.006);
////        return baiduCoordinate;
////    }
//
//
//    /**
//     * @description: 地球坐标系(WGS - 84)转火星坐标系(GCJ)
//     * @param:
//     * @return:
//     * @author: xiankun.jiang
//     * @date: 2019/12/2 15:17
//     */
//    public Coordinate wgs84ToMars(Coordinate wgs84Coordinate) {
//        Coordinate marsCoordinate = new Coordinate();
//        BigDecimal wgLon = wgs84Coordinate.getLongitude();
//        BigDecimal wgLat = wgs84Coordinate.getLatitude();
//        Coordinate d = delta(wgs84Coordinate);
//        marsCoordinate.setLatitude(wgLat.add(d.getLatitude()));
//        marsCoordinate.setLongitude(wgLon.add(d.getLongitude()));
//        return marsCoordinate;
//    }
//
//    /**
//     * @description: 火星坐标系(GCJ)转地球坐标系(WGS - 84)
//     * @param:
//     * @return:
//     * @author: xiankun.jiang
//     * @date: 2019/12/2 15:35
//     */
////    public Coordinate marsToWGS84(Coordinate marsCoordinate) {
////        Coordinate d = delta(marsCoordinate);
////        Coordinate wgs84Coordinate = new Coordinate();
////        wgs84Coordinate.setLatitude(marsCoordinate.getLatitude() - d.getLatitude());
////        wgs84Coordinate.setLongitude(marsCoordinate.getLongitude() - d.getLongitude());
////        return wgs84Coordinate;
////    }
//    private Coordinate delta(Coordinate marsCoordinate) {
//        BigDecimal b180 = new BigDecimal("180.0");
//        BigDecimal b1 = new BigDecimal("1");
//        BigDecimal b105 = new BigDecimal("105.0");
//        BigDecimal b35 = new BigDecimal("35.0");
//
//        BigDecimal dLat = transformLat(marsCoordinate.getLongitude().subtract(b105),
//                marsCoordinate.getLatitude().subtract(b35));
//        BigDecimal dLon = transformLon(marsCoordinate.getLongitude().subtract(b105),
//                marsCoordinate.getLatitude().subtract(b35));
//        BigDecimal radLat = marsCoordinate.getLatitude().divide(b180,11,BigDecimal.ROUND_HALF_UP).multiply(PI);
//        BigDecimal magic = bigMath.sin(radLat);
//        magic = magic.multiply(magic).multiply(ee).subtract(b1);
//        BigDecimal sqrtMagic = bigMath.sqrt(magic);
//
//        dLat = dLat.multiply(b180).divide(b1.subtract(ee).multiply(a).divide((magic.multiply(sqrtMagic)).multiply(PI),11,BigDecimal.ROUND_HALF_UP),11,BigDecimal.ROUND_HALF_UP);
//        dLon = dLon.multiply(b180).divide(a.divide(sqrtMagic).multiply(bigMath.cos(radLat)).multiply(PI),11,BigDecimal.ROUND_HALF_UP);
//        return new Coordinate(dLon, dLat);
//    }
//
//    /*判断是否在国内，不在国内则不做偏移*/
//    private boolean outOfChina(double longitude, double latitude) {
//        return ((longitude < 72.004 || longitude > 137.8347) && (latitude < 0.8293 || latitude > 55.8271));
//    }
//
//
//    private BigDecimal transformLat(BigDecimal x, BigDecimal y) {
//        BigDecimal bn100 = new BigDecimal("-100.0");
//        BigDecimal b2 = new BigDecimal("2.0");
//        BigDecimal b3 = new BigDecimal("3.0");
//        BigDecimal b6 = new BigDecimal("6.0");
//        BigDecimal b01 = new BigDecimal("0.1");
//        BigDecimal b02 = new BigDecimal("0.2");
//        BigDecimal b20 = new BigDecimal("20.00");
//        BigDecimal b30 = new BigDecimal("30.0");
//        BigDecimal b40 = new BigDecimal("40.0");
//        BigDecimal b160 = new BigDecimal("160.0");
//        BigDecimal b12 = new BigDecimal("12.0");
//        BigDecimal ret = bn100
//                .add(b2.multiply(x))
//                .add(b3.multiply(y))
//                .add(b02.multiply(y).multiply(y))
//                .add(b01.multiply(x).multiply(y))
//                .add(b02.multiply(bigMath.sqrt(x.abs())));
////        BigDecimal ret = -100.0
////                + 2.0 * x
////                + 3.0 * y
////                + 0.2 * y * y
////                + 0.1 * x * y
////                + 0.2 * Math.sqrt(Math.abs(x));
//        ret = ret.add(b20.multiply(bigMath.sin(b6.multiply(x).multiply(PI))).add(b20.multiply(bigMath.sin(b2.multiply(x).multiply(PI)))).multiply(b2).divide(b3,11,BigDecimal.ROUND_HALF_UP));
////        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
//        ret = ret.add(b20.multiply(bigMath.sin(y.multiply(PI))).add(b40.multiply(bigMath.sin(y.divide(b3,11,BigDecimal.ROUND_HALF_UP).multiply(PI)))).multiply(b2).divide(b3,11,BigDecimal.ROUND_HALF_UP));
////        ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
//        BigDecimal b320 = new BigDecimal("320.0");
//        ret = ret.add(b160.multiply(bigMath.sin(y.divide(b12,11,BigDecimal.ROUND_HALF_UP).multiply(PI))).add(b320.multiply(bigMath.sin(y.multiply(PI).divide(b30,11,BigDecimal.ROUND_HALF_UP)))).multiply(b2).divide(b3,11,BigDecimal.ROUND_HALF_UP));
////        ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
//        return ret;
//    }
//
//    private BigDecimal transformLon(BigDecimal x, BigDecimal y) {
//        BigDecimal b300 = new BigDecimal("300.0");
//        BigDecimal b2 = new BigDecimal("2.0");
//        BigDecimal b01 = new BigDecimal("0.1");
//        BigDecimal b20 = new BigDecimal("20.0");
//        BigDecimal b6 = new BigDecimal("6.0");
//        BigDecimal b3 = new BigDecimal("3.0");
//        BigDecimal b40 = new BigDecimal("40.0");
//        BigDecimal b150 = new BigDecimal("150.0");
//        BigDecimal b12 = new BigDecimal("12.0");
//        BigDecimal b30 = new BigDecimal("30.0");
//
//        BigDecimal ret = b300
//                .add(x)
//                .add(b2.multiply(y))
//                .add(b01.multiply(x).multiply(x))
//                .add(b01.multiply(x).multiply(y))
//                .add(b01.multiply(bigMath.sqrt(x.abs())));
//
//        ret = ret.add(b20.multiply(bigMath.sin(b6.multiply(x).multiply(PI)))
//                .add(b20.multiply(bigMath.sin(b2.multiply(x).multiply(PI))))
//                .multiply(b2).divide(b3,11,BigDecimal.ROUND_HALF_UP));
//
//        ret = ret.add(b20.multiply(bigMath.sin(x.multiply(PI)))
//                .add(b40.multiply(bigMath.sin(x.divide(b3,11,BigDecimal.ROUND_HALF_UP).multiply(PI))))
//                .multiply(b2).divide(b3,11,BigDecimal.ROUND_HALF_UP));
//
//        ret = ret.add(b150.multiply(bigMath.sin(x.divide(b12,11,BigDecimal.ROUND_HALF_UP).multiply(PI)))
//                .add(b300.multiply(bigMath.sin(x.divide(b30,11,BigDecimal.ROUND_HALF_UP).multiply(PI))))
//                .multiply(b2).divide(b3,11,BigDecimal.ROUND_HALF_UP));
//        return ret;
//    }
//
//    public static void main(String[] argv) {
////        谷歌地图：27.9470444288,110.7778974511
////        百度地图：27.953272056,110.7843283057
////        腾讯高德：27.94703494,110.7778992
////        谷歌地球：27.95039,110.77293
//        CoordinateUtils coordinateUtils = new CoordinateUtils();
//
//        Coordinate wgsCoordinate = new Coordinate(new BigDecimal("110.7778974511"),new BigDecimal("27.9470444288"));
////        Coordinate wgsCoordinate = new Coordinate(110.77293,27.95039);
//        Coordinate marsCoordinate = coordinateUtils.wgs84ToMars(wgsCoordinate);
////        Coordinate baiduCoordinate = coordinateUtils.marsTobaidu(marsCoordinate);
//        System.out.print("");
////        Coordinate mCoordinate = coordinateUtils.baiduTomars(baiduCoordinate);
////        Coordinate wCoordinate = coordinateUtils.marsToWGS84(mCoordinate);
//
//
//        System.out.print("");
//    }
//}
