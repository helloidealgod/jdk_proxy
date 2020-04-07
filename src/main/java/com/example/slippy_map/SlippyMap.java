package com.example.slippy_map;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/4/7 11:34
 */
public class SlippyMap {
    public static void main(String[] args) {
        int zoom = 18;
        double lon = 28.076381;
        double lat = 110.607207;
        System.out.println("https://tile.openstreetmap.org/" + getTileNumber(lat, lon, zoom) + ".png");

        int[] tile = getTile(lat, lon, zoom);
        int[] pixerIndex = getPixer(lat, lon, zoom);
        double[] coordinate0 = getCoordinate(tile[0], tile[1], 0, 0, zoom);
        double[] coordinate = getCoordinate(tile[0], tile[1], pixerIndex[0], pixerIndex[1], zoom);
        System.out.println("");
    }

    public static String getTileNumber(final double lat, final double lon, final int zoom) {
        int xtile = (int) Math.floor((lon + 180) / 360 * (1 << zoom));
        int ytile = (int) Math.floor((1 - Math.log(Math.tan(Math.toRadians(lat)) + 1 / Math.cos(Math.toRadians(lat))) / Math.PI) / 2 * (1 << zoom));
        if (xtile < 0) {
            xtile = 0;
        }
        if (xtile >= (1 << zoom)) {
            xtile = ((1 << zoom) - 1);
        }
        if (ytile < 0) {
            ytile = 0;
        }
        if (ytile >= (1 << zoom)) {
            ytile = ((1 << zoom) - 1);
        }
        return ("" + zoom + "/" + xtile + "/" + ytile);
    }

    public static int[] getTile(final double lat, final double lon, final int zoom) {
        int xtile = (int) Math.floor((lon + 180) / 360 * Math.pow(2, zoom));
        int ytile = (int) Math.floor(
                (1 - Math.log(Math.tan(Math.toRadians(lat)) + 1 / Math.cos(Math.toRadians(lat))) / (2 * Math.PI)) * Math.pow(2, zoom)
        );
//        if (xtile < 0) {
//            xtile = 0;
//        }
//        if (xtile >= (1 << zoom)) {
//            xtile = ((1 << zoom) - 1);
//        }
//        if (ytile < 0) {
//            ytile = 0;
//        }
//        if (ytile >= (1 << zoom)) {
//            ytile = ((1 << zoom) - 1);
//        }
        int[] tile = new int[2];
        tile[0] = xtile;
        tile[1] = ytile;
        return tile;
    }

    public static int[] getPixer(final double lat, final double lon, final int zoom) {
        int pixerX = (int) Math.floor((lon + 180) / 360 * Math.pow(2, zoom) * 256 % 256);
        int pixerY = (int) Math.floor(
                (1 - Math.log(Math.tan(Math.toRadians(lat)) + 1 / Math.cos(Math.toRadians(lat))) / (2 * Math.PI)) * Math.pow(2, zoom) * 256 % 256 + 0.5
        );
        int[] tile = new int[2];
        tile[0] = pixerX;
        tile[1] = pixerY;
        return tile;
    }

    public static double[] getCoordinate(final int tileX, final int tileY, final int pixelX, final int pixelY, final int zoom) {
        double lon = (tileX + pixelX / 256) / Math.pow(2, zoom) * 360 - 180;
        double v = Math.PI - 2 * Math.PI * (tileY + pixelY / 256) / Math.pow(2, zoom);
        double sinh = (Math.exp(v) - Math.exp(-v)) / 2;
        double lat = Math.atan(sinh) * 180 / Math.PI;

        double[] tile = new double[2];
        tile[0] = lon;
        tile[1] = lat;
        return tile;
    }
}
